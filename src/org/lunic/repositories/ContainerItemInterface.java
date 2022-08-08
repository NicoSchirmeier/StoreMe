package org.lunic.repositories;

import org.lunic.data.*;

import java.util.ArrayList;

 /**
 * Used for positive GRASP example low coupling
  * TODO 1 Put example into documentation
 */
public interface ContainerItemInterface {
    ArrayList<Item> getAllItems();
}
