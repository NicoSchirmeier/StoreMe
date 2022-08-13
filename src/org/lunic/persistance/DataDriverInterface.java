package org.lunic.persistance;

import java.util.ArrayList;

public interface DataDriverInterface {
    ArrayList<Record> read();
    void save(ArrayList<Record> records);
}
