package org.lunic.repositories;

import org.lunic.data.Container;

import java.util.ArrayList;

 /**
 * Used for positive GRASP example low coupling
  * TODO 1 Put example into documentation
 */
public interface ContainerRepository {
    void Create(Container container);

    ArrayList<Container> Read();

    void Update(Container containerToBeUpdated,
                Container updatedContainer);

    void Delete(Container container);
}
