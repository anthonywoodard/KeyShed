package com.anthonywoodard.keyshed.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests for User domain.
 *
 * @author Anthony Woodard
 */
public class UserTest {
    User user = new User();
    
    @Test
    public void testUserName() {
      byte[] name = "Joe".getBytes();
      user.setUsername(name);
      assertEquals(name, user.getUsername());
    }

    @Test
    public void testUserPassword() {
      byte[] pass = "test".getBytes();
      user.setUserPassword(pass);
      assertEquals(pass, user.getUserPassword());
    }
    
    @Test
    public void testUserId() {      
      int uId = 1;
      user.setUserId(uId);      
      assertEquals(uId, user.getUserId());
    }
    
    @Test
    public void testPrimaryUser() {      
      int pU = 0;
      user.setPrimaryUser(pU);
      assertEquals(pU, user.getPrimaryUser());
    }
}
