package ir.piana.business.multishop.module.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteInfo {
    private String title;
    private String description;
    private String tipTitle;
    private String tipDescription;
    private String headerImage;
    private String instagramLink;
    private String whatsappLink;
    private String facebookLink;
    private String telNumber;
}
