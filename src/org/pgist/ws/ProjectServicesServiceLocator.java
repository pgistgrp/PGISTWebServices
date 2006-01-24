/**
 * ProjectServicesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2beta3 Aug 01, 2004 (05:59:22 PDT) WSDL2Java emitter.
 */

package org.pgist.ws;

public class ProjectServicesServiceLocator extends org.apache.axis.client.Service implements org.pgist.ws.ProjectServicesService {

    // Use to get a proxy class for ProjectServices
    private java.lang.String ProjectServices_address = "http://pgistdev.geog.washington.edu:8080/pgistws/services/ProjectServices";

    public java.lang.String getProjectServicesAddress() {
        return ProjectServices_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ProjectServicesWSDDServiceName = "ProjectServices";

    public java.lang.String getProjectServicesWSDDServiceName() {
        return ProjectServicesWSDDServiceName;
    }

    public void setProjectServicesWSDDServiceName(java.lang.String name) {
        ProjectServicesWSDDServiceName = name;
    }

    public org.pgist.ws.ProjectServices getProjectServices() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ProjectServices_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getProjectServices(endpoint);
    }

    public org.pgist.ws.ProjectServices getProjectServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.pgist.ws.ProjectServicesSoapBindingStub _stub = new org.pgist.ws.ProjectServicesSoapBindingStub(portAddress, this);
            _stub.setPortName(getProjectServicesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setProjectServicesEndpointAddress(java.lang.String address) {
        ProjectServices_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.pgist.ws.ProjectServices.class.isAssignableFrom(serviceEndpointInterface)) {
                org.pgist.ws.ProjectServicesSoapBindingStub _stub = new org.pgist.ws.ProjectServicesSoapBindingStub(new java.net.URL(ProjectServices_address), this);
                _stub.setPortName(getProjectServicesWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ProjectServices".equals(inputPortName)) {
            return getProjectServices();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.pgist.org", "ProjectServicesService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.pgist.org", "ProjectServices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("ProjectServices".equals(portName)) {
            setProjectServicesEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
