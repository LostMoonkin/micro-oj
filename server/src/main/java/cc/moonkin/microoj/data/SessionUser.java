package cc.moonkin.microoj.data;

import cc.moonkin.microoj.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author anchun
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser {

    private Long id;

    private String obsId;

    private Role role;

    private Date expire;

    private String token;
}
