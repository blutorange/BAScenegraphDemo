package de.homelab.madgaksha.ba.mi15.cgca.scenegraph;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Der mit dieser Annoation versehene Knoten wird als Weltknoten genommen, als als Top-Level-Element.
 * @author madgaksha
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WorldNode {
}
