package com.solmaz.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "selected_options", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectedOption {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "selected_option_id")
    private String selectedOptionId;
    @ManyToOne
    private User selector;
    @ManyToOne
    private PollOption option;
}
