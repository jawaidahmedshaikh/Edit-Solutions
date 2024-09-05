package com.segsoftware.model
{
	import mx.collections.XMLListCollection;
	
	/**
	 * Like the back-end, the front-end does require the building of [some] models.
	 * In the absense of true object entities, the building of such models is
	 * encapsulated here.
	 */ 
	public class SEGModelBuilder
	{
		private static var segModelBuilder:SEGModelBuilder;
		
		/**
		 * During app-entry, users will "build" ContractClients for the 
		 * current Segment. This building process really involves selecting a series
		 * of CodeTableDef values, those being:
		 * LIFERELATIONTYPE, CLASS, TABLERATING, RATEDGENDER, UNDERWRITINGCLASS. 
		 * 
		 * There really is no natural SEG equivalent since it is the collected
		 * disparate information that will finally generate a new ClientRole
		 * and new ContractClient on the back side.
		 * 
		 * The ClientRoleUIVO looks as follows:
		 * 
		 * <ClientRoleUIVO> // repeated for every RoleTypeCT (LIFERELATIONTYPE) in the CodeTableDef and populated upfront
		 * 		<RoleTypeCode/> 
		 * 		<RoleTypeDesc/>
		 *  	<ClassTypeCode/> // to be populated by user selection
		 *  	<TableRatingCode/> // to be populated by user selection
		 *  	<RatedGenderCode/> // to be populated by user selection
		 * 		<UnderwritingClassCode/> // to be populated by user selection
		 *      <BeneficiaryAllocation/> // to be populated by user selection
		 *      <BeneficiaryAllocationType/> // to be populated by user selection
		 * 		<BeneficiaryRelationshipToInsured/> // to be populated by user selection
		 * </ClientRoleUIVO>.
		 */ 
		public function buildCandidateClientRoleUIVOs():XMLListCollection
		{
			var candidateClientRoleUIVOs:XMLListCollection = new XMLListCollection();

			for each (var lifeRelationTypeCodeTableVO:XML in CodeTable.getInstance().clientRoleCodeTableVOs)
			{
				var code:String = lifeRelationTypeCodeTableVO.Code;
				
				var desc:String = lifeRelationTypeCodeTableVO.CodeDesc;
				
				var clientRoleUIVO:XML = 
				<ClientRoleUIVO>
					<RoleTypeCode>{code}</RoleTypeCode>
					<RoleTypeDesc>{desc}</RoleTypeDesc>
					<ClassTypeCode/>
					<TableRatingCode/>
					<RatedGenderCode/>
					<UnderwritingClassCode/>
					<BeneficiaryAllocation/>
					<BeneficiaryAllocationType/>
					<BeneficiaryRelationshipToInsured/>
				</ClientRoleUIVO>;
				
				candidateClientRoleUIVOs.addItem(clientRoleUIVO);
			}
			
			return candidateClientRoleUIVOs;
		}
		
		/**
		 * Every request made to a a SEG backend supporting service is formatted as a SEGRequestVO and always
		 * assumes this form:
		 * 
		 * <SEGRequestVO>
		 * 	<Service/> The service name (e.g. "Billing")
		 * 	<Operation/> The operation to invoke that is associated with the service (e.g. "getBillGroupNotPaid")
		 * 	<RequestParameters> // repeated for as many name/value pairs as are required.
		 * 		<Name1>Value1</Name1> 
		 * 		<Name2>Value2</Name2>
		 * 		<MyXML>	
		 * 			<Foo1>
		 * 				<Foo2/>
		 * 			</Foo2>
		 * 		</MyXML>
		 * 		etc.
		 * 	</RequestParameters>
		 * </SEGRequestVO>
		 */ 
		public function buildSEGRequestVO(serviceName:String, serviceOperation:String, serviceOperator:String, operationRequestParameters:XMLListCollection):XML
		{
			var segRequestVO:XML = <SEGRequestVO/>;
			
			var service:XML = <Service/>;
			
			service.appendChild(serviceName);
			
			var operation:XML = <Operation/>;
			
			operation.appendChild(serviceOperation);
			
			var operator:XML = <Operator/>;
			
			operator.appendChild(serviceOperator);
						
			var allRequestParameters:XML = <RequestParameters/>;
			
			// Build the structure...
			segRequestVO.appendChild(service);
			
			segRequestVO.appendChild(operation);
			
			segRequestVO.appendChild(operator);
			
			segRequestVO.appendChild(allRequestParameters);
			
			for each (var requestParameter:XML in operationRequestParameters)
			{
				allRequestParameters.appendChild(requestParameter);					
			}
			
			return segRequestVO;
		}
		
		/**
		 * The Bill amounts can be adjusted. There is no natural VO
		 * for this. As such, we have created:
		 * 
		 * <AdjustmentVO>
		 * 		<BillPK/>
		 * 		<BilledAmount/>
		 * 		<PaidAmount/>
		 * </AdjustmentVO>.
		 * 
		 * @param billPK the XML element containing the BillVO.BillPK
		 * @param paidAmount the XML element containing the BillVO.PaidAmount
		 * @return an AdjustVO Element containing the specified BillPK and PaidAmount elements
		 */ 
		public function buildAdjustmentVO(billPK:XML, billedAmount:XML, paidAmount:XML):XML
		{
			var adjustmentVO:XML = <AdjustmentVO/>;
			
			adjustmentVO.appendChild(billPK);
			
			adjustmentVO.appendChild(billedAmount);
			
			adjustmentVO.appendChild(paidAmount);
			
			return adjustmentVO;			
		}
		
		/**
		 * //TODO
		 * This should contact the backend service that supplies 
		 * stubbed-out VOs as XML. That table does not yet exist
		 * as of this writing.
		 */ 
		public function buildBatchProgressLogVO(daysAdded:int, daysAddedReasonCT:String, description:String):XML
		{
			var batchProgressLogVO:XML = 
			
				<BatchProgressLogVO>
					<BatchProgressLogPK/>
					<BatchContractSetupFK/>
					<DaysAdded>{daysAdded}</DaysAdded>
					<DaysAddedReasonCT>{daysAddedReasonCT}</DaysAddedReasonCT>
					<Description>{description}</Description>
				</BatchProgressLogVO>;
				
			return batchProgressLogVO;
		}
		
		/**
		 * The user maintains a manual log of how many policies received for each product type.
		 */ 
		public function buildBatchProductLogVO(numberOfAppsReceived:String, estimatedAnnualPremium:String):XML
		{
			var batchProductLogVO:XML = 
			
			<BatchProductLogVO>
				<BatchProductLogPK/>
				<FilteredProductFK/>
				<BatchContractSetupFK/>
				<NumberOfAppsReceived>{numberOfAppsReceived}</NumberOfAppsReceived>
				<EstimatedAnnualPremium>{estimatedAnnualPremium}</EstimatedAnnualPremium>
			</BatchProductLogVO>;
			
			return batchProductLogVO;
		}
		
		/**
		 * Each Segment that is submitted to the backend during appEntry is partially defined
		 * by default information coming from the BatchContractSetupVO. This
		 * information is tracked via a SegmentInformationVO represented as:
		 * <SegmentInformationVO>
    	 * 		<FilteredProductPK/>
		 *	    <ContractNumber/>
		 *	    <DepartmentLocationPK/>
		 *	    <Coverage>Traditional</Coverage>   NOTE: This is hard-coded here for now.  It allows the CoverageDialog to work.  It will be changed once we make Coverage an option.
		 *	    <FaceAmount/>
 		 *	    <EffectiveDate/>
		 *	    <ApplicationSignedDate/>
		 *	    <ApplicationReceivedDate/>
		 *	    <ApplicationSignedStateCT/>
		 *      <IssueStateCT/>
		 *	    <DeathBenefitOptionCT/>
		 *   	<NonForfeitureOptionCT/>
		 * 		<EstateOfTheInsured/>
		 * 		<DeductionAmountOverride/>
		 * 		<DeductionAmountEffectiveDate/>
		 * </SegmentInformationVO>
		 * 
		 */ 
		public function buildSegmentInformationVO(filteredProductFK:String, 
													deathBenefitOptionCT:String, 
													nonForfeitureOptionCT:String,
													effectiveDate:String,
													applicationSignedDate:String,
													applicationReceivedDate:String,
													applicationSignedStateCT:String,
													ratedGenderCT:String,
													underwritingClass:String,
													groupPlan:String,
													issueStateCT:String,
													exchangeInd:String,
													scheduledPremium:String,
													mecStatusCT:String):XML
		{
			var segmentInformationVO:XML =
				<SegmentInformationVO>
				    <FilteredProductPK>{filteredProductFK}</FilteredProductPK>
				    <ContractNumber/>
				    <DepartmentLocationPK/>
				    <Coverage>Traditional</Coverage>      	
				    <FaceAmount/>
				    <EffectiveDate>{effectiveDate}</EffectiveDate>
				    <ApplicationSignedDate>{applicationSignedDate}</ApplicationSignedDate>
				    <ApplicationReceivedDate>{applicationReceivedDate}</ApplicationReceivedDate>
				    <ApplicationSignedStateCT>{applicationSignedStateCT}</ApplicationSignedStateCT>
				    <RatedGenderCT>{ratedGenderCT}</RatedGenderCT>
				    <UnderwritingClassCT>{underwritingClass}</UnderwritingClassCT>
				    <GroupPlan>{groupPlan}</GroupPlan>
				    <IssueStateCT>{issueStateCT}</IssueStateCT>
				    <DeathBenefitOptionCT>{deathBenefitOptionCT}</DeathBenefitOptionCT>
				    <NonForfeitureOptionCT>{nonForfeitureOptionCT}</NonForfeitureOptionCT>
				    <ExchangeInd>{exchangeInd}</ExchangeInd>
					<ScheduledPremium>{scheduledPremium}</ScheduledPremium>
					<mecStatusCT>{mecStatusCT}</mecStatusCT>
				    <EstateOfTheInsured>N</EstateOfTheInsured>
				    <DeductionAmountOverride/>
				    <DeductionAmountEffectiveDate/>
				</SegmentInformationVO>;
				
			return segmentInformationVO;
		}
		
		/**
		 * During AppEntry, there is a [lot] of information to track when building the clients. 
		 * The user will have selected the ClientDetailVOs to use, and for each ClientDetailVO,
		 * will establish roles, underwriting information, etc. To track this information, we
		 * use a convoluted document with the following structure:
		 * 
		 * <AppEntryClientDetailUIVO> // repeated for each ClientDetailVO used in the current AppEntry.
		 * 		<ClientDetailVO> // the actual client
		 * 		<RelationshipToEmployeeCT>
		 * 		<EmployeeIdentification>
		 * 		<CandidateClientRoleUIVOs> // These track the the "candidate" roles, underwriting info, splits, etc.
		 * 			<ClientRoleUIVO> // repeated for every Role defined in CodeTable. 
		 * 		<SelectedClientRoleUIVOs> // From the "candidate" roles, there are selected ones.
		 * 			<ClientRoleUIVO> // repeated for evey selected ClientRoleUIVO from the candidate ones
		 * 
		 */ 		
		public function buildAppEntryClienDetailUIVO(clientDetailVO:XML):XML
		{
			var appEntryClientDetailUIVO:XML = <AppEntryClientDetailUIVO/>;
			
			appEntryClientDetailUIVO.ClientDetailVO = clientDetailVO;
			
			appEntryClientDetailUIVO.RelationshipToEmployeeCT = <RelationshipToEmployeeCT/>;
			
			appEntryClientDetailUIVO.EmployeeIdentification = <EmployeeIdentification/>;
			
			appEntryClientDetailUIVO.CandidateClientRoleUIVOs = <CandidateClientRoleUIVOs/>;
			
			appEntryClientDetailUIVO.SelectedClientRoleUIVOs = <SelectedClientRoleUIVOs/>;
			
			// Prepopulate candidate roles...
			var candidateClientRoleUIVOs:XMLListCollection = buildCandidateClientRoleUIVOs();
			
			var candidateClientRoleUIVOsXML:XML = <CandidateClientRoleUIVOs/>;
			
			for each (var candidateClientRoleUIVO:XML in candidateClientRoleUIVOs)
			{
				appEntryClientDetailUIVO.CandidateClientRoleUIVOs.CandidateClientRoleUIVO = candidateClientRoleUIVO;
			}
			
			return appEntryClientDetailUIVO;								
		}
		
		/**
		 * Uses the specified AppEntryClientDetailVO to build the following:
		 * 
		 * 	<ClientInformationVO>                  
		 *    <ClientDetailVO/>
		 *    <RelationshipToEmployeeCT/>
		 * 	  <EmployeeIdentification/>
		 *    <ContractClientInformationVO>      // one role for each ClientInformationVO
		 *        <RoleTypeCT/>
		 *        <ClassCT/>
		 *        <TableRatingCT/>
		 *        <RatedGenderCT/>
		 *        <UnderwritingClassCT/>
		 *        <PrimaryBeneficiaryAllocation/>
		 *        <SecondaryBeneficiaryAllocation/>
		 *    </ContractClientInformationVO>
		 *    <QuestionnaireResponseVO/> // repeats for every question answered
		 * </ClientInformationVO>
		 */ 
		public function buildClientInformationVO(appEntryClientDetailVO:XML, clientRoleUIVO:XML):XML
		{
			var clientInformationVO:XML = <ClientInformationVO/>;
			
			clientInformationVO.ClientDetailPK = appEntryClientDetailVO.ClientDetailVO[0].ClientDetailPK[0];
			
			clientInformationVO.RelationshipToEmployeeCT = appEntryClientDetailVO.RelationshipToEmployeeCT;
			
			clientInformationVO.EmployeeIdentification = appEntryClientDetailVO.EmployeeIdentification;
			
			var contractClientInformationVO:XML = <ContractClientInformationVO/>;
			
			contractClientInformationVO.RoleTypeCT = clientRoleUIVO.RoleTypeCode[0].toString();
			
			contractClientInformationVO.ClassCT = clientRoleUIVO.ClassTypeCode[0].toString();
			
			contractClientInformationVO.TableRatingCT = clientRoleUIVO.TableRatingCode[0].toString();
			
			contractClientInformationVO.BeneficiaryAllocation = clientRoleUIVO.BeneficiaryAllocation[0].toString();
			
			contractClientInformationVO.BeneficiaryAllocationType = clientRoleUIVO.BeneficiaryAllocationType[0].toString();
			
			contractClientInformationVO.BeneficiaryRelationshipToInsured = clientRoleUIVO.BeneficiaryRelationshipToInsured[0].toString();
			
			clientInformationVO.ContractClientInformationVO += contractClientInformationVO;
			
			// Loop through, copy and then add the questionnaireResponseVOs.
			var questionnaireResponseVOs:XMLList = appEntryClientDetailVO.QuestionnaireResponseVO;
			
			for each (var questionnaireResponseVO:XML in questionnaireResponseVOs)
			{
				var questionnaireResponseVOCopy:XML = questionnaireResponseVO.copy();
				
				// we don't want everything, just the questionnaireResponseVO
				delete questionnaireResponseVOCopy.FilteredQuestionnaireVO;
				
				clientInformationVO.appendChild(questionnaireResponseVOCopy);
			}
			
			return clientInformationVO;
		}
		
		/**
		 * Performing New Business quotes expects the return of:
		 * - AnnualPremium
		 * - DeductionAmount
		 * .. represented as:
		 * <QuoteVO>
		 * 	<AnnualPremium/>
		 * 	<DeductionAmount/>
		 * </QuoteVO>
		 */ 
		public function buildQuoteVO(annualPremium:String, deductionAmount:String):XML
		{
			var quoteVO:XML = 
			<QuoteVO>
				<AnnualPremium>{annualPremium}</AnnualPremium>
				<DeductionAmount>{deductionAmount}</DeductionAmount>
			</QuoteVO>;
			
			return quoteVO;			
		}
		
		/**
		 * Users can answer a questionnaire for the current ClientDetail being added as 
		 * a ContractClient during AppEntry. This builds a composition since it is 
		 * assumed that specified FilteredQuestionnaireVO also contains its associated
		 * QuestionniareVO. The final composition resembles the following:
		 * <QuestionnaireResponseVO>
		 * 		<FilteredQuestionnaireFK> // taken from the specified filteredQuestionnaireVO
		 * 		<ResponseCT/> // the answer to be supplied by the user
		 * 		<FilteredQuestionnaireVO/>
		 * 			<QuestionnaireVO/>
		 * </QuestionnaireResponseVO>
		 */ 
		public function buildQuestionnaireResponseVO(filteredQuestionnaireVO:XML):XML
		{
			var contractQuestionnaireVO:XML = <QuestionnaireResponseVO>
												<FilteredQuestionnaireFK>{filteredQuestionnaireVO.FilteredQuestionnairePK[0].text()}</FilteredQuestionnaireFK>
												<ResponseCT/>
											  </QuestionnaireResponseVO>;
											  
			contractQuestionnaireVO.appendChild(filteredQuestionnaireVO);
			
			return contractQuestionnaireVO;											  	
		}
		
		/**
		 * 
		 */ 
		public function buildConversionTemplateVO(templateName:String, templateDescription:String):XML
		{
			var conversionTemplateVO:XML = <ConversionTemplateVO>
												<TemplateName>{templateName}</TemplateName>
												<TemplateDescription>{templateDescription}</TemplateDescription>
											</ConversionTemplateVO>;
											
			return conversionTemplateVO;												
		}
		
		/**
		 * We would like to represent the PRASEDocumentVO as "candidate" and "selected".
		 * However, once selected, the user can flag the PRASEDocumentVO as "IsRoot" 
		 * or not. PRASEDocumentVO does not have this field. We wrap the
		 * PRASEDocumentVO and add the "IsRoot" element.
		 * 
		 * @param praseDocumentVO the VO to wrap
		 * @param isRoot 'Y' if the specified VO is to be a root level document - default to 'N'
		 * 
		 * @return
		 * 			<PRASEDocumentWrapperVO>
		 * 				{praseDocumentVO} // the specified one
		 * 				<IsRoot></IsRoot>
		 * 			</PRASEDocumentWrapperVO>
		 */ 
		public function buildPRASEDocumentWrapperVO(praseDocumentVO:XML, isRoot:String = 'N'):XML
		{
			var praseDocumentWrapperVO:XML = <PRASEDocumentWrapperVO>
													{praseDocumentVO}
													<IsRoot>{isRoot}</IsRoot>
												</PRASEDocumentWrapperVO>;
												
			return praseDocumentWrapperVO;												
		}
		
		/**
		 * The singleton.
		 */ 
		public static function getInstance():SEGModelBuilder
		{
			if (segModelBuilder == null)
			{
				segModelBuilder = new SEGModelBuilder();
			}
			 
			return segModelBuilder;
		}
		
		
	}
}