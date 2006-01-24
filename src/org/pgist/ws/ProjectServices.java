/**
 * ProjectServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2beta3 Aug 01, 2004 (05:59:22 PDT) WSDL2Java emitter.
 */

package org.pgist.ws;

public interface ProjectServices extends java.rmi.Remote {
    public void main(java.lang.String[] in0) throws java.rmi.RemoteException;
    public org.pgist.datamodel.TransportationProject getProjectInfo(java.lang.Long in0) throws java.rmi.RemoteException;
    public void addProject(org.pgist.datamodel.TransportationProject in0) throws java.rmi.RemoteException;
}
