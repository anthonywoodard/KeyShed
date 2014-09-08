package com.anthonywoodard.keyshed.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests for Key domain.
 *
 * @author Anthony Woodard
 */
public class KeyTest {
    Key key = new Key();
    
    @Test
    public void testKeyId() {
      int kId = 0;
      key.setKeyId(kId);
      assertEquals(kId, key.getKeyId());
    }

    @Test
    public void testKeyTitle() {
      byte[] kTitle = "test".getBytes();
      key.setKeyTitle(kTitle);
      assertEquals(kTitle, key.getKeyTitle());
    }
    
    @Test
    public void testKeyUsername() {      
      byte[] kName = "test".getBytes();
      key.setKeyUsername(kName);
      assertEquals(kName, key.getKeyUsername());
    }
    
    @Test
    public void testKeyPassword() {
      byte[] pass = "test123".getBytes();
      key.setKeyPassword(pass);
      assertEquals(pass, key.getKeyPassword());
    }
    
    @Test
    public void testKeyUrl() {
      byte[] url = "test.com".getBytes();
      key.setKeyUrl(url);
      assertEquals(url, key.getKeyUrl());
    }
    
    @Test
    public void testKeyCategory() {
      byte[] cat = "test".getBytes();
      key.setKeyCategory(cat);
      assertEquals(cat, key.getKeyCategory());
    }
}
