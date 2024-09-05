package casetracking.usecase;

import casetracking.*;
import client.*;
import contract.*;
import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.component.*;
import edit.portal.exceptions.*;

import java.util.*;

import event.*;

/*
 * User: sprasad
 * Date: Apr 8, 2005
 * Time: 1:59:48 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

public interface CasetrackingUseCase extends IUseCase
{
    public void accessCaseTracking();

    public void saveOrUpdateNote(CasetrackingNote casetrackingNote, Long clientDetailPK);

    public void deleteNote(Long casetrackingNote);

    public ClientDetail updateClientDeathStatus(ClientDetail clientDetail, String transactionType);

    public CaseTrackingQuoteVO performDeathQuote(EDITDate disbursmentDate, EDITBigDecimal beneAllocationPct, Long segmentPK);

    public void saveLumpSumTransaction(CasetrackingLog casetrackingLog, Long contractClientKey, int taxYear,
                                       EDITBigDecimal amountOverride, EDITBigDecimal interestOverride, String zeroInterestIndicator,
                                       Withholding withholding, Charge[] charges, Segment segment, boolean isSuppContract, int sequenceNumber)
                                        throws EDITEventException, PortalEditingException, Exception;

    public void updateExistingBeneficiary(Long contractClientPK, String relationship, EDITDate terminationDate, String terminationReason, CasetrackingLog casetrackingLog, String splitEqualInd, EDITBigDecimal allocationAmount);

    public void createNewBeneficiary(Long clientDetailPK, Long segmentPK, String beneRole, ContractClient contractClient, CasetrackingLog casetrackingLog, String splitEqualInd, EDITBigDecimal allocationAmount);

    public void processSpousalContinuation(CasetrackingLog casetrackingLog, Long contractClientPK);

    public void createNewContract(Segment newSegment, Long contractClientPK, boolean openClaim, CasetrackingLog casetrackingLog, Map fundMap)
                                    throws EDITEventException, PortalEditingException, Exception; 

    public void updateExistingContract(Payout payout, Long contractClientPK, String originalSegmentPK, String operator, EDITDate effectiveDate);

    public CaseTrackingQuoteVO performPayoutQuote(Payout payout, Long segmentPK, Long contractClientPK);

    public void updateCaseRequirement(CaseRequirement caseRequirement);

    public void deleteCaseRequirement(CaseRequirement caseRequirement);

    public void associateNonManualFilteredRequirementsToClient(ClientDetail clientDetail, FilteredRequirement[] filteredRequirements);

    public void deathTransactionProcess(CasetrackingLog casetrackingLog, Long contractClientPK, String condtionCT) throws Exception, PortalEditingException;

    public void updateClientProcess(ClientDetail clientDetail);

    public void associateFilteredRequirementToClient(ClientDetail clientDetail, FilteredRequirement filteredRequirement);

    public void processStretchIRA(Segment originalSegment, ContractClient beneContractClient, CasetrackingLog casetrackingLog, RequiredMinDistribution rmd, Set beneficiaries, String printLIne1, String printLine2);

    public void processRiderClaim(CasetrackingLog casetrackingLog, Long segmentPK, String careType, EDITDate dateOfDeath, String claimType, String conditionCT, String authorizedSignatureCT, EDITBigDecimal amountOverride, EDITBigDecimal interestOverride);
}
