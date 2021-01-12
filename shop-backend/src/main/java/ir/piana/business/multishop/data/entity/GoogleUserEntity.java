package ir.piana.business.multishop.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "users_seq", initialValue = 1, sequenceName = "users_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoogleUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vavishka_seq")
    @Column(name = "id")
    private long Id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile")
    private String mobile;
    @Column
    private String password;
    @Column(name = "email_verified")
    private boolean emailVerified;
    @Column
    private String name;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column
    private String locale;
    @Column(name = "family_name")
    private String familyName;
    @Column(name = "given_name")
    private String givenName;
}
