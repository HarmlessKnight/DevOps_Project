package com.example.personal_finance_tracker.config;

import com.example.personal_finance_tracker.Exceptions.UnauthorizedAccessException;
import com.example.personal_finance_tracker.Models.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.getPrincipal() instanceof UserPrincipal){
            return ((UserPrincipal)auth.getPrincipal()).getId();
        }
        throw new UnauthorizedAccessException("User not Authenticated");
    }


    public static void CheckOwnership(Long OwnerId){
        Long currentUserId = getCurrentUserId();
        if(!currentUserId.equals(OwnerId)){
            throw new UnauthorizedAccessException("Unauthorized access");
        }
    }

    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserPrincipal;
    }

}
