package samples.second.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "second_module_table")
public class SecondEntity {

    @Id
    private Integer id;
    @Column(name = "second_name")
    private String secondName;

}
