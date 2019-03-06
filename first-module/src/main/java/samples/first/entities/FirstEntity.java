package samples.first.entities;

import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "first_module_table")
public class FirstEntity {

    @Id
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
}
