package net.shtyftu.ubiquode.web.security.junk;

/**
 * @author shtyftu
 */

public class FIlterProvider {//} implements AuthenticationProvider {


//     This would be a JPA repository to snag your user entities
//    private final UserRepository userRepository;

//    @Autowired
//    public DemoAuthenticationProvider(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        DemoAuthenticationToken demoAuthentication = (DemoAuthenticationToken) authentication;
//        User user = userRepository.find(demoAuthentication.getId());
//
//        if(user == null){
//            throw new UnknownUserException("Could not find user with ID: " + demoAuthentication.getId());
//        }
//
//        return user;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return DemoAuthenticationToken.class.isAssignableFrom(authentication);
//    }

}
