package pers.missp.springjwt.jwt;

import com.sun.deploy.net.HttpResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.connector.Response;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        System.out.println(request.getRequestURI());
        if (request.getRequestURI().startsWith("/public-api/")){
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = request.getHeader("Authorization");
//        System.out.println(jwtToken);
        if (validateToken(jwtToken)){
            Claims claims = Jwts.parser().setSigningKey(JwtContsant.SIGNINGKEY).parseClaimsJws(jwtToken.replace("Bearer ","")).getBody();
            String username = claims.getSubject();
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String)claims.get("authorities"));
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,null, authorities);
            SecurityContextHolder.getContext().setAuthentication(token);
            long remainTokenTime = (Integer)claims.get("exp") -  (Long)System.currentTimeMillis() / 1000;
//            System.out.println("remain:" + remainTokenTime);
//            System.out.println("timeout:" + (JwtContsant.EXPIRATION / 1000));
            // refresh when remain time less then 1 / 4
            if (remainTokenTime < (JwtContsant.EXPIRATION / 1000) / 4){
//                System.out.println("refresh token!");
                response.setHeader("Authorization", Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + JwtContsant.EXPIRATION))
                        .signWith(SignatureAlgorithm.HS512, JwtContsant.SIGNINGKEY)
                        .compact());
            }
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(401);
            PrintWriter responseWriter = response.getWriter();
            responseWriter.write("invalid token");
            responseWriter.flush();
            responseWriter.close();
        }


    }

    private boolean validateToken(String jwtToken){
        try {
            Claims claims = Jwts.parser().setSigningKey(JwtContsant.SIGNINGKEY).parseClaimsJws(jwtToken.replace("Bearer ","")).getBody();
        } catch (Exception e){
            return false;
        }
        return true;
    }

}
