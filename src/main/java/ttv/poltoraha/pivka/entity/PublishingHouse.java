package ttv.poltoraha.pivka.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
