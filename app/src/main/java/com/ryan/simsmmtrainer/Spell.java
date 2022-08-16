package com.ryan.simsmmtrainer;

public class Spell {

    public Spell(String _name, int _firstContra, int _secondContra, int _imageId)
    {
        m_name = _name;
        m_imageId = _imageId;
        m_firstContra = _firstContra;
        m_secondContra = _secondContra;
        m_defined = -1;
    }

    public Boolean isDefined()
    {
        return m_defined != -1;
    }

    public void define(int _defined)
    {
        m_defined = _defined;
    }

    public String getName()
    {
        return m_name;
    }

    public int getImageId()
    {
        return m_imageId;
    }

    public int getFirstContra()
    {
        return m_firstContra;
    }

    public int getSecondContra()
    {
        return m_secondContra;
    }

    public int getDefined()
    {
        return m_defined;
    }

    private String m_name;

    private int m_firstContra;
    private int m_secondContra;
    private int m_imageId;

    private int m_defined;
}
