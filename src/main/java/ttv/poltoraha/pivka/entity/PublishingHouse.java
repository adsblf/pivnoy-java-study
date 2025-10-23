package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity(name="publishing_house")
@Data
@ToString
public class PublishingHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String address;

    @OneToMany(mappedBy = "publishingHouse", cascade = CascadeType.ALL)
    private List<Book> books;
}
