package com.zookeeper.zookeeper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ZooAnimal {
  @Id
  @GeneratedValue
  public Integer id;
  public String name;
  public String species;
  public String origin;
  public Boolean isHungry;

  public ZooAnimal() {
  }
}