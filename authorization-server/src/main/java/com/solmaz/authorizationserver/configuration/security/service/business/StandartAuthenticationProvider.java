package com.solmaz.security.service.business;

import com.solmaz.dto.request.AuthenticationRequest;
import com.solmaz.dto.request.AddUserRequest;
import com.solmaz.dto.response.ActiveDirectoryAuthenticationResponse;
import com.solmaz.entity.User;
import com.solmaz.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class StandartAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        HttpHeaders httpHeaders= new HttpHeaders();
        var email = authentication.getPrincipal().toString();
        try {
            httpHeaders.add("Authorization","Basic dmJ0aWs6eHUyOTg1SzVmNTkjbm0tWD9A");
            var authenticationResponse = restTemplate.exchange("https://adlogin.solmaz.com/login", HttpMethod.POST, new HttpEntity<Object>(new AuthenticationRequest(authentication.getPrincipal().toString(), authentication.getCredentials().toString()),httpHeaders), ActiveDirectoryAuthenticationResponse.class);
            if(!authenticationResponse.getBody().isSuccess())
                throw new UsernameNotFoundException("wrong email or password ");
        }catch (Exception exception){
            throw exception;
        }
        if(!userRepository.findByEmail(email).isPresent()) {
            String command = "powershell.exe  Get-ADUser -Filter {Mail -eq '"+email+"'} -Properties DisplayName,PasswordLastSet,Title"; //json format-> ConvertTo-Json |
            try {
                var addUserRequest = powerShellList(command).orElseThrow(()->new IllegalArgumentException("user informations not found"));
                var user = modelMapper.map(addUserRequest, User.class);
                user.setEmail(email);
                userRepository.save(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        var usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),null,new ArrayList<>());

        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    public static Optional<AddUserRequest> powerShellList(String command) throws IOException {

        //String command = "powershell.exe  your command";
        //Getting the version

        Process powerShellProcess = Runtime.getRuntime().exec(command);

        // Getting the results
        powerShellProcess.getOutputStream().close();
        String line;
        System.out.println("Standard Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                powerShellProcess.getInputStream(),"CP857"));
        String name = null;
        String title = null;
        while ((line = stdout.readLine()) != null) {
            if(line.startsWith("Name")) {
                for (int i=0;i<line.length();i++){
                    if(line.charAt(i)==':') {
                        name = line.substring(i + 1);
                        System.err.println(name);
                    }
                }
            } else if (line.startsWith("Title")) {
                for (int i=0;i<line.length();i++){
                    if(line.charAt(i)==':') {
                        title = line.substring(i + 1);
                        System.err.println(title);
                        var addUserRequest= new AddUserRequest();
                        addUserRequest.setFullname(name.trim());
                        addUserRequest.setTitle(title.trim());
                        return Optional.of(addUserRequest);
                    }
                }
                
            }
        }
        stdout.close();
        System.out.println("Standard Error:");
        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                powerShellProcess.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            System.out.println(line);
        }
        stderr.close();
        System.out.println("Done");
        return Optional.empty();
    }
}
