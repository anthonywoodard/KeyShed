package com.anthonywoodard.keyshed.view;

import com.anthonywoodard.keyshed.domain.Key;
import java.util.List;

/**
 *
 * @author Anthony Woodard
 */
public interface KeyView extends View {
  public Key newKey(List<String> params);
  public Key updateKey(List<String> params);
  public void showKeys(List<Key> keys, String layout);   
  public Key findKey(List<String> params);
  public Key deleteKey();
  public void showHelp();
  public List<Key> importKeys(List<String> params);
  public void exportKeys(List<String> params, List<Key> keys);
  public List<String> listKeys(List<String> params);
}
