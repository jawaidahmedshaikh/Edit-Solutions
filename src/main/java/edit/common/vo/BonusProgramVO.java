/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package edit.common.vo;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class BonusProgramVO.
 * 
 * @version $Revision$ $Date$
 */
public class BonusProgramVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bonusProgramPK
     */
    private long _bonusProgramPK;

    /**
     * keeps track of state for field: _bonusProgramPK
     */
    private boolean _has_bonusProgramPK;

    /**
     * Field _contractCodeCT
     */
    private java.lang.String _contractCodeCT;

    /**
     * Field _commissionLevelCT
     */
    private java.lang.String _commissionLevelCT;

    /**
     * Field _bonusNameCT
     */
    private java.lang.String _bonusNameCT;

    /**
     * Field _produceCheckInd
     */
    private java.lang.String _produceCheckInd;

    /**
     * Field _frequencyCT
     */
    private java.lang.String _frequencyCT;

    /**
     * Field _bonusStartDate
     */
    private java.lang.String _bonusStartDate;

    /**
     * Field _bonusStopDate
     */
    private java.lang.String _bonusStopDate;

    /**
     * Field _applicationReceivedStopDate
     */
    private java.lang.String _applicationReceivedStopDate;

    /**
     * Field _premiumStopDate
     */
    private java.lang.String _premiumStopDate;

    /**
     * Field _includeAdditionalPremiumInd
     */
    private java.lang.String _includeAdditionalPremiumInd;

    /**
     * Field _specificParticipantStatus
     */
    private java.lang.String _specificParticipantStatus;

    /**
     * Field _specificProductStatus
     */
    private java.lang.String _specificProductStatus;

    /**
     * Field _specificCommissionProfStatus
     */
    private java.lang.String _specificCommissionProfStatus;

    /**
     * Field _specificHierarchyLevelStatus
     */
    private java.lang.String _specificHierarchyLevelStatus;

    /**
     * Field _applicationReceivedStartDate
     */
    private java.lang.String _applicationReceivedStartDate;

    /**
     * Field _nextCheckDate
     */
    private java.lang.String _nextCheckDate;

    /**
     * Field _baseProgramCompletedInd
     */
    private java.lang.String _baseProgramCompletedInd;

    /**
     * Field _contributingProfileVOList
     */
    private java.util.Vector _contributingProfileVOList;

    /**
     * Field _participatingAgentVOList
     */
    private java.util.Vector _participatingAgentVOList;

    /**
     * Field _premiumLevelVOList
     */
    private java.util.Vector _premiumLevelVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BonusProgramVO() {
        super();
        _contributingProfileVOList = new Vector();
        _participatingAgentVOList = new Vector();
        _premiumLevelVOList = new Vector();
    } //-- edit.common.vo.BonusProgramVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addContributingProfileVO
     * 
     * @param vContributingProfileVO
     */
    public void addContributingProfileVO(edit.common.vo.ContributingProfileVO vContributingProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContributingProfileVO.setParentVO(this.getClass(), this);
        _contributingProfileVOList.addElement(vContributingProfileVO);
    } //-- void addContributingProfileVO(edit.common.vo.ContributingProfileVO) 

    /**
     * Method addContributingProfileVO
     * 
     * @param index
     * @param vContributingProfileVO
     */
    public void addContributingProfileVO(int index, edit.common.vo.ContributingProfileVO vContributingProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContributingProfileVO.setParentVO(this.getClass(), this);
        _contributingProfileVOList.insertElementAt(vContributingProfileVO, index);
    } //-- void addContributingProfileVO(int, edit.common.vo.ContributingProfileVO) 

    /**
     * Method addParticipatingAgentVO
     * 
     * @param vParticipatingAgentVO
     */
    public void addParticipatingAgentVO(edit.common.vo.ParticipatingAgentVO vParticipatingAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vParticipatingAgentVO.setParentVO(this.getClass(), this);
        _participatingAgentVOList.addElement(vParticipatingAgentVO);
    } //-- void addParticipatingAgentVO(edit.common.vo.ParticipatingAgentVO) 

    /**
     * Method addParticipatingAgentVO
     * 
     * @param index
     * @param vParticipatingAgentVO
     */
    public void addParticipatingAgentVO(int index, edit.common.vo.ParticipatingAgentVO vParticipatingAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vParticipatingAgentVO.setParentVO(this.getClass(), this);
        _participatingAgentVOList.insertElementAt(vParticipatingAgentVO, index);
    } //-- void addParticipatingAgentVO(int, edit.common.vo.ParticipatingAgentVO) 

    /**
     * Method addPremiumLevelVO
     * 
     * @param vPremiumLevelVO
     */
    public void addPremiumLevelVO(edit.common.vo.PremiumLevelVO vPremiumLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPremiumLevelVO.setParentVO(this.getClass(), this);
        _premiumLevelVOList.addElement(vPremiumLevelVO);
    } //-- void addPremiumLevelVO(edit.common.vo.PremiumLevelVO) 

    /**
     * Method addPremiumLevelVO
     * 
     * @param index
     * @param vPremiumLevelVO
     */
    public void addPremiumLevelVO(int index, edit.common.vo.PremiumLevelVO vPremiumLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPremiumLevelVO.setParentVO(this.getClass(), this);
        _premiumLevelVOList.insertElementAt(vPremiumLevelVO, index);
    } //-- void addPremiumLevelVO(int, edit.common.vo.PremiumLevelVO) 

    /**
     * Method enumerateContributingProfileVO
     */
    public java.util.Enumeration enumerateContributingProfileVO()
    {
        return _contributingProfileVOList.elements();
    } //-- java.util.Enumeration enumerateContributingProfileVO() 

    /**
     * Method enumerateParticipatingAgentVO
     */
    public java.util.Enumeration enumerateParticipatingAgentVO()
    {
        return _participatingAgentVOList.elements();
    } //-- java.util.Enumeration enumerateParticipatingAgentVO() 

    /**
     * Method enumeratePremiumLevelVO
     */
    public java.util.Enumeration enumeratePremiumLevelVO()
    {
        return _premiumLevelVOList.elements();
    } //-- java.util.Enumeration enumeratePremiumLevelVO() 

    /**
     * Note: hashCode() has not been overriden
     * 
     * @param obj
     */
    public boolean equals(java.lang.Object obj)
    {
        if ( this == obj )
            return true;
        
        if (super.equals(obj)==false)
            return false;
        
        if (obj instanceof BonusProgramVO) {
        
            BonusProgramVO temp = (BonusProgramVO)obj;
            if (this._bonusProgramPK != temp._bonusProgramPK)
                return false;
            if (this._has_bonusProgramPK != temp._has_bonusProgramPK)
                return false;
            if (this._contractCodeCT != null) {
                if (temp._contractCodeCT == null) return false;
                else if (!(this._contractCodeCT.equals(temp._contractCodeCT))) 
                    return false;
            }
            else if (temp._contractCodeCT != null)
                return false;
            if (this._commissionLevelCT != null) {
                if (temp._commissionLevelCT == null) return false;
                else if (!(this._commissionLevelCT.equals(temp._commissionLevelCT))) 
                    return false;
            }
            else if (temp._commissionLevelCT != null)
                return false;
            if (this._bonusNameCT != null) {
                if (temp._bonusNameCT == null) return false;
                else if (!(this._bonusNameCT.equals(temp._bonusNameCT))) 
                    return false;
            }
            else if (temp._bonusNameCT != null)
                return false;
            if (this._produceCheckInd != null) {
                if (temp._produceCheckInd == null) return false;
                else if (!(this._produceCheckInd.equals(temp._produceCheckInd))) 
                    return false;
            }
            else if (temp._produceCheckInd != null)
                return false;
            if (this._frequencyCT != null) {
                if (temp._frequencyCT == null) return false;
                else if (!(this._frequencyCT.equals(temp._frequencyCT))) 
                    return false;
            }
            else if (temp._frequencyCT != null)
                return false;
            if (this._bonusStartDate != null) {
                if (temp._bonusStartDate == null) return false;
                else if (!(this._bonusStartDate.equals(temp._bonusStartDate))) 
                    return false;
            }
            else if (temp._bonusStartDate != null)
                return false;
            if (this._bonusStopDate != null) {
                if (temp._bonusStopDate == null) return false;
                else if (!(this._bonusStopDate.equals(temp._bonusStopDate))) 
                    return false;
            }
            else if (temp._bonusStopDate != null)
                return false;
            if (this._applicationReceivedStopDate != null) {
                if (temp._applicationReceivedStopDate == null) return false;
                else if (!(this._applicationReceivedStopDate.equals(temp._applicationReceivedStopDate))) 
                    return false;
            }
            else if (temp._applicationReceivedStopDate != null)
                return false;
            if (this._premiumStopDate != null) {
                if (temp._premiumStopDate == null) return false;
                else if (!(this._premiumStopDate.equals(temp._premiumStopDate))) 
                    return false;
            }
            else if (temp._premiumStopDate != null)
                return false;
            if (this._includeAdditionalPremiumInd != null) {
                if (temp._includeAdditionalPremiumInd == null) return false;
                else if (!(this._includeAdditionalPremiumInd.equals(temp._includeAdditionalPremiumInd))) 
                    return false;
            }
            else if (temp._includeAdditionalPremiumInd != null)
                return false;
            if (this._specificParticipantStatus != null) {
                if (temp._specificParticipantStatus == null) return false;
                else if (!(this._specificParticipantStatus.equals(temp._specificParticipantStatus))) 
                    return false;
            }
            else if (temp._specificParticipantStatus != null)
                return false;
            if (this._specificProductStatus != null) {
                if (temp._specificProductStatus == null) return false;
                else if (!(this._specificProductStatus.equals(temp._specificProductStatus))) 
                    return false;
            }
            else if (temp._specificProductStatus != null)
                return false;
            if (this._specificCommissionProfStatus != null) {
                if (temp._specificCommissionProfStatus == null) return false;
                else if (!(this._specificCommissionProfStatus.equals(temp._specificCommissionProfStatus))) 
                    return false;
            }
            else if (temp._specificCommissionProfStatus != null)
                return false;
            if (this._specificHierarchyLevelStatus != null) {
                if (temp._specificHierarchyLevelStatus == null) return false;
                else if (!(this._specificHierarchyLevelStatus.equals(temp._specificHierarchyLevelStatus))) 
                    return false;
            }
            else if (temp._specificHierarchyLevelStatus != null)
                return false;
            if (this._applicationReceivedStartDate != null) {
                if (temp._applicationReceivedStartDate == null) return false;
                else if (!(this._applicationReceivedStartDate.equals(temp._applicationReceivedStartDate))) 
                    return false;
            }
            else if (temp._applicationReceivedStartDate != null)
                return false;
            if (this._nextCheckDate != null) {
                if (temp._nextCheckDate == null) return false;
                else if (!(this._nextCheckDate.equals(temp._nextCheckDate))) 
                    return false;
            }
            else if (temp._nextCheckDate != null)
                return false;
            if (this._baseProgramCompletedInd != null) {
                if (temp._baseProgramCompletedInd == null) return false;
                else if (!(this._baseProgramCompletedInd.equals(temp._baseProgramCompletedInd))) 
                    return false;
            }
            else if (temp._baseProgramCompletedInd != null)
                return false;
            if (this._contributingProfileVOList != null) {
                if (temp._contributingProfileVOList == null) return false;
                else if (!(this._contributingProfileVOList.equals(temp._contributingProfileVOList))) 
                    return false;
            }
            else if (temp._contributingProfileVOList != null)
                return false;
            if (this._participatingAgentVOList != null) {
                if (temp._participatingAgentVOList == null) return false;
                else if (!(this._participatingAgentVOList.equals(temp._participatingAgentVOList))) 
                    return false;
            }
            else if (temp._participatingAgentVOList != null)
                return false;
            if (this._premiumLevelVOList != null) {
                if (temp._premiumLevelVOList == null) return false;
                else if (!(this._premiumLevelVOList.equals(temp._premiumLevelVOList))) 
                    return false;
            }
            else if (temp._premiumLevelVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getApplicationReceivedStartDateReturns the value of
     * field 'applicationReceivedStartDate'.
     * 
     * @return the value of field 'applicationReceivedStartDate'.
     */
    public java.lang.String getApplicationReceivedStartDate()
    {
        return this._applicationReceivedStartDate;
    } //-- java.lang.String getApplicationReceivedStartDate() 

    /**
     * Method getApplicationReceivedStopDateReturns the value of
     * field 'applicationReceivedStopDate'.
     * 
     * @return the value of field 'applicationReceivedStopDate'.
     */
    public java.lang.String getApplicationReceivedStopDate()
    {
        return this._applicationReceivedStopDate;
    } //-- java.lang.String getApplicationReceivedStopDate() 

    /**
     * Method getBaseProgramCompletedIndReturns the value of field
     * 'baseProgramCompletedInd'.
     * 
     * @return the value of field 'baseProgramCompletedInd'.
     */
    public java.lang.String getBaseProgramCompletedInd()
    {
        return this._baseProgramCompletedInd;
    } //-- java.lang.String getBaseProgramCompletedInd() 

    /**
     * Method getBonusNameCTReturns the value of field
     * 'bonusNameCT'.
     * 
     * @return the value of field 'bonusNameCT'.
     */
    public java.lang.String getBonusNameCT()
    {
        return this._bonusNameCT;
    } //-- java.lang.String getBonusNameCT() 

    /**
     * Method getBonusProgramPKReturns the value of field
     * 'bonusProgramPK'.
     * 
     * @return the value of field 'bonusProgramPK'.
     */
    public long getBonusProgramPK()
    {
        return this._bonusProgramPK;
    } //-- long getBonusProgramPK() 

    /**
     * Method getBonusStartDateReturns the value of field
     * 'bonusStartDate'.
     * 
     * @return the value of field 'bonusStartDate'.
     */
    public java.lang.String getBonusStartDate()
    {
        return this._bonusStartDate;
    } //-- java.lang.String getBonusStartDate() 

    /**
     * Method getBonusStopDateReturns the value of field
     * 'bonusStopDate'.
     * 
     * @return the value of field 'bonusStopDate'.
     */
    public java.lang.String getBonusStopDate()
    {
        return this._bonusStopDate;
    } //-- java.lang.String getBonusStopDate() 

    /**
     * Method getCommissionLevelCTReturns the value of field
     * 'commissionLevelCT'.
     * 
     * @return the value of field 'commissionLevelCT'.
     */
    public java.lang.String getCommissionLevelCT()
    {
        return this._commissionLevelCT;
    } //-- java.lang.String getCommissionLevelCT() 

    /**
     * Method getContractCodeCTReturns the value of field
     * 'contractCodeCT'.
     * 
     * @return the value of field 'contractCodeCT'.
     */
    public java.lang.String getContractCodeCT()
    {
        return this._contractCodeCT;
    } //-- java.lang.String getContractCodeCT() 

    /**
     * Method getContributingProfileVO
     * 
     * @param index
     */
    public edit.common.vo.ContributingProfileVO getContributingProfileVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contributingProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContributingProfileVO) _contributingProfileVOList.elementAt(index);
    } //-- edit.common.vo.ContributingProfileVO getContributingProfileVO(int) 

    /**
     * Method getContributingProfileVO
     */
    public edit.common.vo.ContributingProfileVO[] getContributingProfileVO()
    {
        int size = _contributingProfileVOList.size();
        edit.common.vo.ContributingProfileVO[] mArray = new edit.common.vo.ContributingProfileVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContributingProfileVO) _contributingProfileVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContributingProfileVO[] getContributingProfileVO() 

    /**
     * Method getContributingProfileVOCount
     */
    public int getContributingProfileVOCount()
    {
        return _contributingProfileVOList.size();
    } //-- int getContributingProfileVOCount() 

    /**
     * Method getFrequencyCTReturns the value of field
     * 'frequencyCT'.
     * 
     * @return the value of field 'frequencyCT'.
     */
    public java.lang.String getFrequencyCT()
    {
        return this._frequencyCT;
    } //-- java.lang.String getFrequencyCT() 

    /**
     * Method getIncludeAdditionalPremiumIndReturns the value of
     * field 'includeAdditionalPremiumInd'.
     * 
     * @return the value of field 'includeAdditionalPremiumInd'.
     */
    public java.lang.String getIncludeAdditionalPremiumInd()
    {
        return this._includeAdditionalPremiumInd;
    } //-- java.lang.String getIncludeAdditionalPremiumInd() 

    /**
     * Method getNextCheckDateReturns the value of field
     * 'nextCheckDate'.
     * 
     * @return the value of field 'nextCheckDate'.
     */
    public java.lang.String getNextCheckDate()
    {
        return this._nextCheckDate;
    } //-- java.lang.String getNextCheckDate() 

    /**
     * Method getParticipatingAgentVO
     * 
     * @param index
     */
    public edit.common.vo.ParticipatingAgentVO getParticipatingAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _participatingAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ParticipatingAgentVO) _participatingAgentVOList.elementAt(index);
    } //-- edit.common.vo.ParticipatingAgentVO getParticipatingAgentVO(int) 

    /**
     * Method getParticipatingAgentVO
     */
    public edit.common.vo.ParticipatingAgentVO[] getParticipatingAgentVO()
    {
        int size = _participatingAgentVOList.size();
        edit.common.vo.ParticipatingAgentVO[] mArray = new edit.common.vo.ParticipatingAgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ParticipatingAgentVO) _participatingAgentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ParticipatingAgentVO[] getParticipatingAgentVO() 

    /**
     * Method getParticipatingAgentVOCount
     */
    public int getParticipatingAgentVOCount()
    {
        return _participatingAgentVOList.size();
    } //-- int getParticipatingAgentVOCount() 

    /**
     * Method getPremiumLevelVO
     * 
     * @param index
     */
    public edit.common.vo.PremiumLevelVO getPremiumLevelVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _premiumLevelVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PremiumLevelVO) _premiumLevelVOList.elementAt(index);
    } //-- edit.common.vo.PremiumLevelVO getPremiumLevelVO(int) 

    /**
     * Method getPremiumLevelVO
     */
    public edit.common.vo.PremiumLevelVO[] getPremiumLevelVO()
    {
        int size = _premiumLevelVOList.size();
        edit.common.vo.PremiumLevelVO[] mArray = new edit.common.vo.PremiumLevelVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PremiumLevelVO) _premiumLevelVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PremiumLevelVO[] getPremiumLevelVO() 

    /**
     * Method getPremiumLevelVOCount
     */
    public int getPremiumLevelVOCount()
    {
        return _premiumLevelVOList.size();
    } //-- int getPremiumLevelVOCount() 

    /**
     * Method getPremiumStopDateReturns the value of field
     * 'premiumStopDate'.
     * 
     * @return the value of field 'premiumStopDate'.
     */
    public java.lang.String getPremiumStopDate()
    {
        return this._premiumStopDate;
    } //-- java.lang.String getPremiumStopDate() 

    /**
     * Method getProduceCheckIndReturns the value of field
     * 'produceCheckInd'.
     * 
     * @return the value of field 'produceCheckInd'.
     */
    public java.lang.String getProduceCheckInd()
    {
        return this._produceCheckInd;
    } //-- java.lang.String getProduceCheckInd() 

    /**
     * Method getSpecificCommissionProfStatusReturns the value of
     * field 'specificCommissionProfStatus'.
     * 
     * @return the value of field 'specificCommissionProfStatus'.
     */
    public java.lang.String getSpecificCommissionProfStatus()
    {
        return this._specificCommissionProfStatus;
    } //-- java.lang.String getSpecificCommissionProfStatus() 

    /**
     * Method getSpecificHierarchyLevelStatusReturns the value of
     * field 'specificHierarchyLevelStatus'.
     * 
     * @return the value of field 'specificHierarchyLevelStatus'.
     */
    public java.lang.String getSpecificHierarchyLevelStatus()
    {
        return this._specificHierarchyLevelStatus;
    } //-- java.lang.String getSpecificHierarchyLevelStatus() 

    /**
     * Method getSpecificParticipantStatusReturns the value of
     * field 'specificParticipantStatus'.
     * 
     * @return the value of field 'specificParticipantStatus'.
     */
    public java.lang.String getSpecificParticipantStatus()
    {
        return this._specificParticipantStatus;
    } //-- java.lang.String getSpecificParticipantStatus() 

    /**
     * Method getSpecificProductStatusReturns the value of field
     * 'specificProductStatus'.
     * 
     * @return the value of field 'specificProductStatus'.
     */
    public java.lang.String getSpecificProductStatus()
    {
        return this._specificProductStatus;
    } //-- java.lang.String getSpecificProductStatus() 

    /**
     * Method hasBonusProgramPK
     */
    public boolean hasBonusProgramPK()
    {
        return this._has_bonusProgramPK;
    } //-- boolean hasBonusProgramPK() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method removeAllContributingProfileVO
     */
    public void removeAllContributingProfileVO()
    {
        _contributingProfileVOList.removeAllElements();
    } //-- void removeAllContributingProfileVO() 

    /**
     * Method removeAllParticipatingAgentVO
     */
    public void removeAllParticipatingAgentVO()
    {
        _participatingAgentVOList.removeAllElements();
    } //-- void removeAllParticipatingAgentVO() 

    /**
     * Method removeAllPremiumLevelVO
     */
    public void removeAllPremiumLevelVO()
    {
        _premiumLevelVOList.removeAllElements();
    } //-- void removeAllPremiumLevelVO() 

    /**
     * Method removeContributingProfileVO
     * 
     * @param index
     */
    public edit.common.vo.ContributingProfileVO removeContributingProfileVO(int index)
    {
        java.lang.Object obj = _contributingProfileVOList.elementAt(index);
        _contributingProfileVOList.removeElementAt(index);
        return (edit.common.vo.ContributingProfileVO) obj;
    } //-- edit.common.vo.ContributingProfileVO removeContributingProfileVO(int) 

    /**
     * Method removeParticipatingAgentVO
     * 
     * @param index
     */
    public edit.common.vo.ParticipatingAgentVO removeParticipatingAgentVO(int index)
    {
        java.lang.Object obj = _participatingAgentVOList.elementAt(index);
        _participatingAgentVOList.removeElementAt(index);
        return (edit.common.vo.ParticipatingAgentVO) obj;
    } //-- edit.common.vo.ParticipatingAgentVO removeParticipatingAgentVO(int) 

    /**
     * Method removePremiumLevelVO
     * 
     * @param index
     */
    public edit.common.vo.PremiumLevelVO removePremiumLevelVO(int index)
    {
        java.lang.Object obj = _premiumLevelVOList.elementAt(index);
        _premiumLevelVOList.removeElementAt(index);
        return (edit.common.vo.PremiumLevelVO) obj;
    } //-- edit.common.vo.PremiumLevelVO removePremiumLevelVO(int) 

    /**
     * Method setApplicationReceivedStartDateSets the value of
     * field 'applicationReceivedStartDate'.
     * 
     * @param applicationReceivedStartDate the value of field
     * 'applicationReceivedStartDate'.
     */
    public void setApplicationReceivedStartDate(java.lang.String applicationReceivedStartDate)
    {
        this._applicationReceivedStartDate = applicationReceivedStartDate;
        
        super.setVoChanged(true);
    } //-- void setApplicationReceivedStartDate(java.lang.String) 

    /**
     * Method setApplicationReceivedStopDateSets the value of field
     * 'applicationReceivedStopDate'.
     * 
     * @param applicationReceivedStopDate the value of field
     * 'applicationReceivedStopDate'.
     */
    public void setApplicationReceivedStopDate(java.lang.String applicationReceivedStopDate)
    {
        this._applicationReceivedStopDate = applicationReceivedStopDate;
        
        super.setVoChanged(true);
    } //-- void setApplicationReceivedStopDate(java.lang.String) 

    /**
     * Method setBaseProgramCompletedIndSets the value of field
     * 'baseProgramCompletedInd'.
     * 
     * @param baseProgramCompletedInd the value of field
     * 'baseProgramCompletedInd'.
     */
    public void setBaseProgramCompletedInd(java.lang.String baseProgramCompletedInd)
    {
        this._baseProgramCompletedInd = baseProgramCompletedInd;
        
        super.setVoChanged(true);
    } //-- void setBaseProgramCompletedInd(java.lang.String) 

    /**
     * Method setBonusNameCTSets the value of field 'bonusNameCT'.
     * 
     * @param bonusNameCT the value of field 'bonusNameCT'.
     */
    public void setBonusNameCT(java.lang.String bonusNameCT)
    {
        this._bonusNameCT = bonusNameCT;
        
        super.setVoChanged(true);
    } //-- void setBonusNameCT(java.lang.String) 

    /**
     * Method setBonusProgramPKSets the value of field
     * 'bonusProgramPK'.
     * 
     * @param bonusProgramPK the value of field 'bonusProgramPK'.
     */
    public void setBonusProgramPK(long bonusProgramPK)
    {
        this._bonusProgramPK = bonusProgramPK;
        
        super.setVoChanged(true);
        this._has_bonusProgramPK = true;
    } //-- void setBonusProgramPK(long) 

    /**
     * Method setBonusStartDateSets the value of field
     * 'bonusStartDate'.
     * 
     * @param bonusStartDate the value of field 'bonusStartDate'.
     */
    public void setBonusStartDate(java.lang.String bonusStartDate)
    {
        this._bonusStartDate = bonusStartDate;
        
        super.setVoChanged(true);
    } //-- void setBonusStartDate(java.lang.String) 

    /**
     * Method setBonusStopDateSets the value of field
     * 'bonusStopDate'.
     * 
     * @param bonusStopDate the value of field 'bonusStopDate'.
     */
    public void setBonusStopDate(java.lang.String bonusStopDate)
    {
        this._bonusStopDate = bonusStopDate;
        
        super.setVoChanged(true);
    } //-- void setBonusStopDate(java.lang.String) 

    /**
     * Method setCommissionLevelCTSets the value of field
     * 'commissionLevelCT'.
     * 
     * @param commissionLevelCT the value of field
     * 'commissionLevelCT'.
     */
    public void setCommissionLevelCT(java.lang.String commissionLevelCT)
    {
        this._commissionLevelCT = commissionLevelCT;
        
        super.setVoChanged(true);
    } //-- void setCommissionLevelCT(java.lang.String) 

    /**
     * Method setContractCodeCTSets the value of field
     * 'contractCodeCT'.
     * 
     * @param contractCodeCT the value of field 'contractCodeCT'.
     */
    public void setContractCodeCT(java.lang.String contractCodeCT)
    {
        this._contractCodeCT = contractCodeCT;
        
        super.setVoChanged(true);
    } //-- void setContractCodeCT(java.lang.String) 

    /**
     * Method setContributingProfileVO
     * 
     * @param index
     * @param vContributingProfileVO
     */
    public void setContributingProfileVO(int index, edit.common.vo.ContributingProfileVO vContributingProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contributingProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContributingProfileVO.setParentVO(this.getClass(), this);
        _contributingProfileVOList.setElementAt(vContributingProfileVO, index);
    } //-- void setContributingProfileVO(int, edit.common.vo.ContributingProfileVO) 

    /**
     * Method setContributingProfileVO
     * 
     * @param contributingProfileVOArray
     */
    public void setContributingProfileVO(edit.common.vo.ContributingProfileVO[] contributingProfileVOArray)
    {
        //-- copy array
        _contributingProfileVOList.removeAllElements();
        for (int i = 0; i < contributingProfileVOArray.length; i++) {
            contributingProfileVOArray[i].setParentVO(this.getClass(), this);
            _contributingProfileVOList.addElement(contributingProfileVOArray[i]);
        }
    } //-- void setContributingProfileVO(edit.common.vo.ContributingProfileVO) 

    /**
     * Method setFrequencyCTSets the value of field 'frequencyCT'.
     * 
     * @param frequencyCT the value of field 'frequencyCT'.
     */
    public void setFrequencyCT(java.lang.String frequencyCT)
    {
        this._frequencyCT = frequencyCT;
        
        super.setVoChanged(true);
    } //-- void setFrequencyCT(java.lang.String) 

    /**
     * Method setIncludeAdditionalPremiumIndSets the value of field
     * 'includeAdditionalPremiumInd'.
     * 
     * @param includeAdditionalPremiumInd the value of field
     * 'includeAdditionalPremiumInd'.
     */
    public void setIncludeAdditionalPremiumInd(java.lang.String includeAdditionalPremiumInd)
    {
        this._includeAdditionalPremiumInd = includeAdditionalPremiumInd;
        
        super.setVoChanged(true);
    } //-- void setIncludeAdditionalPremiumInd(java.lang.String) 

    /**
     * Method setNextCheckDateSets the value of field
     * 'nextCheckDate'.
     * 
     * @param nextCheckDate the value of field 'nextCheckDate'.
     */
    public void setNextCheckDate(java.lang.String nextCheckDate)
    {
        this._nextCheckDate = nextCheckDate;
        
        super.setVoChanged(true);
    } //-- void setNextCheckDate(java.lang.String) 

    /**
     * Method setParticipatingAgentVO
     * 
     * @param index
     * @param vParticipatingAgentVO
     */
    public void setParticipatingAgentVO(int index, edit.common.vo.ParticipatingAgentVO vParticipatingAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _participatingAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vParticipatingAgentVO.setParentVO(this.getClass(), this);
        _participatingAgentVOList.setElementAt(vParticipatingAgentVO, index);
    } //-- void setParticipatingAgentVO(int, edit.common.vo.ParticipatingAgentVO) 

    /**
     * Method setParticipatingAgentVO
     * 
     * @param participatingAgentVOArray
     */
    public void setParticipatingAgentVO(edit.common.vo.ParticipatingAgentVO[] participatingAgentVOArray)
    {
        //-- copy array
        _participatingAgentVOList.removeAllElements();
        for (int i = 0; i < participatingAgentVOArray.length; i++) {
            participatingAgentVOArray[i].setParentVO(this.getClass(), this);
            _participatingAgentVOList.addElement(participatingAgentVOArray[i]);
        }
    } //-- void setParticipatingAgentVO(edit.common.vo.ParticipatingAgentVO) 

    /**
     * Method setPremiumLevelVO
     * 
     * @param index
     * @param vPremiumLevelVO
     */
    public void setPremiumLevelVO(int index, edit.common.vo.PremiumLevelVO vPremiumLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _premiumLevelVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPremiumLevelVO.setParentVO(this.getClass(), this);
        _premiumLevelVOList.setElementAt(vPremiumLevelVO, index);
    } //-- void setPremiumLevelVO(int, edit.common.vo.PremiumLevelVO) 

    /**
     * Method setPremiumLevelVO
     * 
     * @param premiumLevelVOArray
     */
    public void setPremiumLevelVO(edit.common.vo.PremiumLevelVO[] premiumLevelVOArray)
    {
        //-- copy array
        _premiumLevelVOList.removeAllElements();
        for (int i = 0; i < premiumLevelVOArray.length; i++) {
            premiumLevelVOArray[i].setParentVO(this.getClass(), this);
            _premiumLevelVOList.addElement(premiumLevelVOArray[i]);
        }
    } //-- void setPremiumLevelVO(edit.common.vo.PremiumLevelVO) 

    /**
     * Method setPremiumStopDateSets the value of field
     * 'premiumStopDate'.
     * 
     * @param premiumStopDate the value of field 'premiumStopDate'.
     */
    public void setPremiumStopDate(java.lang.String premiumStopDate)
    {
        this._premiumStopDate = premiumStopDate;
        
        super.setVoChanged(true);
    } //-- void setPremiumStopDate(java.lang.String) 

    /**
     * Method setProduceCheckIndSets the value of field
     * 'produceCheckInd'.
     * 
     * @param produceCheckInd the value of field 'produceCheckInd'.
     */
    public void setProduceCheckInd(java.lang.String produceCheckInd)
    {
        this._produceCheckInd = produceCheckInd;
        
        super.setVoChanged(true);
    } //-- void setProduceCheckInd(java.lang.String) 

    /**
     * Method setSpecificCommissionProfStatusSets the value of
     * field 'specificCommissionProfStatus'.
     * 
     * @param specificCommissionProfStatus the value of field
     * 'specificCommissionProfStatus'.
     */
    public void setSpecificCommissionProfStatus(java.lang.String specificCommissionProfStatus)
    {
        this._specificCommissionProfStatus = specificCommissionProfStatus;
        
        super.setVoChanged(true);
    } //-- void setSpecificCommissionProfStatus(java.lang.String) 

    /**
     * Method setSpecificHierarchyLevelStatusSets the value of
     * field 'specificHierarchyLevelStatus'.
     * 
     * @param specificHierarchyLevelStatus the value of field
     * 'specificHierarchyLevelStatus'.
     */
    public void setSpecificHierarchyLevelStatus(java.lang.String specificHierarchyLevelStatus)
    {
        this._specificHierarchyLevelStatus = specificHierarchyLevelStatus;
        
        super.setVoChanged(true);
    } //-- void setSpecificHierarchyLevelStatus(java.lang.String) 

    /**
     * Method setSpecificParticipantStatusSets the value of field
     * 'specificParticipantStatus'.
     * 
     * @param specificParticipantStatus the value of field
     * 'specificParticipantStatus'.
     */
    public void setSpecificParticipantStatus(java.lang.String specificParticipantStatus)
    {
        this._specificParticipantStatus = specificParticipantStatus;
        
        super.setVoChanged(true);
    } //-- void setSpecificParticipantStatus(java.lang.String) 

    /**
     * Method setSpecificProductStatusSets the value of field
     * 'specificProductStatus'.
     * 
     * @param specificProductStatus the value of field
     * 'specificProductStatus'.
     */
    public void setSpecificProductStatus(java.lang.String specificProductStatus)
    {
        this._specificProductStatus = specificProductStatus;
        
        super.setVoChanged(true);
    } //-- void setSpecificProductStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BonusProgramVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BonusProgramVO) Unmarshaller.unmarshal(edit.common.vo.BonusProgramVO.class, reader);
    } //-- edit.common.vo.BonusProgramVO unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
