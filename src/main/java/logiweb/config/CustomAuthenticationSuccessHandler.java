package logiweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_NONE")) {
            httpServletResponse.sendRedirect("/logiweb/hello");
        } else if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/logiweb/admin/users");
        } else if (roles.contains("ROLE_MANAGER")) {
            httpServletResponse.sendRedirect("/logiweb/officer/orders");
        } else if (roles.contains("ROLE_DRIVER")) {
            httpServletResponse.sendRedirect("/logiweb/driver");
        }

    }


}
