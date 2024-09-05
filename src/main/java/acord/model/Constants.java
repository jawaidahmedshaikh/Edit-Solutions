/*
 * User: sdorman
 * Date: Aug 29, 2006
 * Time: 10:27:40 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package acord.model;

public class Constants
{
    public interface OLIBoolean
    {
        // False
        public int OLI_BOOL_FALSE = 0;  

        // True
        public int OLI_BOOL_TRUE = 1;
    }

    /**
     * Represents the type of data contained in the table.
     */
    public interface DataType
    {
        //Array of Booleans
        public int OLI_DATATYPE_ARRBOOL = 9;

        //Array of Currencies
        public int OLI_DATATYPE_ARRCURR = 14;

        //Array of Dates
        public int OLI_DATATYPE_ARRDATE = 16;

        //Array of Decimals
        public int OLI_DATATYPE_ARRDECI = 15;

        //Array of Doubles
        public int OLI_DATATYPE_ARRDBL = 13;

        //Array of Integers
        public int OLI_DATATYPE_ARRINT = 10;

        //Array of Longs
        public int OLI_DATATYPE_ARRLONG = 11;

        //Array of Shorts
        public int OLI_DATATYPE_ARRSHORT = 12;

        //Array of Strings
        public int OLI_DATATYPE_ARRSTR = 17;

        //Boolean
        public int OLI_DATATYPE_BOOL = 0;

        //Currency
        public int OLI_DATATYPE_CURR = 5;

        //Date
        public int OLI_DATATYPE_DATE = 7;

        //Decimal
        public int OLI_DATATYPE_DECI = 6;

        //Double
        public int OLI_DATATYPE_DBL = 4;

        //IDREF
        public int OLI_DATATYPE_IDREF = 19;

        //Integer
        public int OLI_DATATYPE_INT = 1;

        //Long
        public int OLI_DATATYPE_LONG = 2;

        //Percentage
        public int OLI_DATATYPE_PERCENT = 20;

        //Short
        public int OLI_DATATYPE_SHORT = 3;

        //String
        public int OLI_DATATYPE_STR = 8;

        //Time
        public int OLI_DATATYPE_TIME = 18;
    }

    public interface Nation
    {
        //Afghanistan
        public int OLI_NATION_AFGHANISTAN = 93;

        //Albania
        public int OLI_NATION_ALBANIA = 355;

        //Algeria
        public int OLI_NATION_ALGERIA = 213;

        //American Samoa
        public int OLI_NATION_AMERICANSAMOA = 684;

        //Andorra
        public int OLI_NATION_ANDORRA = 376;

        //Angola
        public int OLI_NATION_ANGOLA = 244;

        //Anguilla
        public int OLI_NATION_ANGUILLA = 1001;

        //Antarctica
        public int OLI_NATION_ANTARCTICA = 672;

        //Antigua and Barbuda
        public int OLI_NATION_ANTIGUABARBUDA = 268;

        //Argentina
        public int OLI_NATION_ARGENTINA = 54;

        //Armenia
        public int OLI_NATION_ARMENIA = 374;

        //Aruba
        public int OLI_NATION_ARUBA = 297;

        //Ascension Islands
        public int OLI_NATION_ASCENSIONISLANDS = 247;

        //Australia
        public int OLI_NATION_AUSTRALIA = 61;

        //Austria
        public int OLI_NATION_AUSTRIA = 43;

        //Azerbaijan
        public int OLI_NATION_AZERBAIJAN = 994;

        //Bahamas
        public int OLI_NATION_BAHAMAS = 242;

        //Bahrain
        public int OLI_NATION_BAHRAIN = 973;

        //Bangladesh
        public int OLI_NATION_BANGLADESH = 880;

        //Barbados
        public int OLI_NATION_BARBADOS = 246;

        //Belarus
        public int OLI_NATION_BELARUS = 375;

        //Belgium
        public int OLI_NATION_BELGIUM = 32;

        //Belize
        public int OLI_NATION_BELIZE = 501;

        //Benin
        public int OLI_NATION_BENIN = 229;

        //Bermuda
        public int OLI_NATION_BERMUDA = 441;

        //Bhutan
        public int OLI_NATION_BHUTAN = 975;

        //Bolivia
        public int OLI_NATION_BOLIVIA = 591;

        //Bosnia and Herzegovina
        public int OLI_NATION_BOSNIAHERZEGOVINA = 387;

        //Botswana
        public int OLI_NATION_BOTSWANA = 267;

        //Bouvet Island
        public int OLI_NATION_BOUVETISLAND = 1009;

        //Brazil
        public int OLI_NATION_BRAZIL = 55;

        //British Indian Ocean Territory
        public int OLI_NATION_BRITISHINDIANOCNTERR = 1011;

        //Brunei Darussalam
        public int OLI_NATION_BRUNEI = 673;

        //Bulgaria
        public int OLI_NATION_BULGARIA = 359;

        //Burkina Faso
        public int OLI_NATION_BURKINAFASO = 226;

        //Burma (Union Of Myanmar)
        public int OLI_NATION_BURMA = 95;

        //Burundi
        public int OLI_NATION_BURUNDI = 257;

        //Cambodia
        public int OLI_NATION_CAMBODIA = 855;

        //Cameroon
        public int OLI_NATION_CAMEROON = 237;

        //Canada
        public int OLI_NATION_CANADA = 2;

        //Cape Verde
        public int OLI_NATION_CAPEVERDEISLAND = 238;

        //Cayman Islands
        public int OLI_NATION_CAYMANIS = 345;

        //Central African Republic
        public int OLI_NATION_CENTRALAFRICANREP = 236;

        //Chad
        public int OLI_NATION_CHAD = 235;

        //Chile
        public int OLI_NATION_CHILE = 56;

        //China
        public int OLI_NATION_CHINA = 86;

        //Christmas Island
        public int OLI_NATION_CHRISTMASISLANDS = 1024;

        //Cocoa Islands
        public int OLI_NATION_COCOAISLANDS = 1012;

        //Cocos (Keeling) Islands
        public int OLI_NATION_COCOS = 1007;

        //Colombia
        public int OLI_NATION_COLUMBIA = 57;

        //Comoros
        public int OLI_NATION_COMOROS = 269;

        //Congo
        public int OLI_NATION_CONGO = 1027;

        //Congo, the Democratic Republic of the
        public int OLI_NATION_CONGODEMREP = 271;

        //Cook Islands
        public int OLI_NATION_COOKISLANDS = 682;

        //Costa Rica
        public int OLI_NATION_COSTARICA = 506;

        //Cote d'Ivoire
        public int OLI_NATION_COTEDIVORIE = 1003;

        //Croatia
        public int OLI_NATION_CROATIA = 385;

        //Cuba
        public int OLI_NATION_CUBA = 53;

        //Curacao
        public int OLI_NATION_CURACAO = 1026;

        //Cyprus
        public int OLI_NATION_CYPRUS = 357;

        //Czechloslovakia
        public int OLI_NATION_CZECHLOSLOVAKIA = 42;

        //Czech Republic
        public int OLI_NATION_CZECHREPUBLIC = 420;

        //Denmark
        public int OLI_NATION_DENMARK = 45;

        //Diego Garcia
        public int OLI_NATION_DIEGOGARCIA = 1030;

        //Djibouti
        public int OLI_NATION_DJIBOUTI = 253;

        //Dominica
        public int OLI_NATION_DOMINICA = 767;

        //Dominican Republic
        public int OLI_NATION_DOMINICANREPUBLIC = 809;

        //Ecuador
        public int OLI_NATION_ECUADOR = 593;

        //Egypt
        public int OLI_NATION_EGYPT = 20;

        //El Salvador
        public int OLI_NATION_ELSALVADOR = 503;

        //Equatorial Guinea
        public int OLI_NATION_EQUATORIALGUINEA = 240;

        //Eritrea
        public int OLI_NATION_ERITREA = 1004;

        //Estonia
        public int OLI_NATION_ESTONIA = 372;

        //Ethiopia
        public int OLI_NATION_ETHIOPIA = 251;

        //Falkland Islands (Malvinas)
        public int OLI_NATION_FALKLANDISLANDS = 500;

        //Faroe Islands
        public int OLI_NATION_FAEROEISLANDS = 298;

        //Fiji
        public int OLI_NATION_FIJI = 679;

        //Finland
        public int OLI_NATION_FINLAND = 358;

        //France
        public int OLI_NATION_FRANCE = 33;

        //French Guiana
        public int OLI_NATION_FRENCHGUIANA = 594;

        //French Polynesia
        public int OLI_NATION_FRENCHPOLYNESIA = 689;

        //French Southern Territories
        public int OLI_NATION_FRENCHSOUTHERNTERRITIOIES = 1015;

        //Gabon
        public int OLI_NATION_GABON = 241;

        //Gambia
        public int OLI_NATION_GAMBIA = 220;

        //Georgia
        public int OLI_NATION_GEORGIA = 995;

        //Germany
        public int OLI_NATION_GERMANY = 49;

        //Ghana
        public int OLI_NATION_GHANA = 233;

        //Gilbratar
        public int OLI_NATION_GIBRALTAR = 350;

        //Greece
        public int OLI_NATION_GREECE = 30;

        //Greenland
        public int OLI_NATION_GREENLAND = 299;

        //Grenada
        public int OLI_NATION_CRENADA = 473;

        //Guadeloupe
        public int OLI_NATION_GUADALOUPE = 590;

        //Guam
        public int OLI_NATION_GUAM = 671;

        //Guatemala
        public int OLI_NATION_GUATEMALA = 502;

        //Guernsey
        public int OLI_NATION_GUERNSEY = 360;

        //Guinea
        public int OLI_NATION_GUINEA = 224;

        //Guinea-Bissau
        public int OLI_NATION_GUINEABISSAU = 270;

        //Guyana
        public int OLI_NATION_GUYANA = 592;

        //Haiti
        public int OLI_NATION_HAITI = 509;

        //Heard Island and McDonald Islands
        public int OLI_NATION_HEARDISLISLAND = 1016;

        //Holy See (Vatican City State)
        public int OLI_NATION_HOLYSEE = 1029;

        //Honduras
        public int OLI_NATION_HONDURAS = 504;

        //Hong Kong
        public int OLI_NATION_HONGKONG = 852;

        //Hungary
        public int OLI_NATION_HUNGARY = 36;

        //Iceland
        public int OLI_NATION_ICELAND = 354;

        //India
        public int OLI_NATION_INDIA = 91;

        //Indonesia
        public int OLI_NATION_INDONESIA = 62;

        //Iran, Islamic Republic of
        public int OLI_NATION_IRAN = 98;

        //Iraq
        public int OLI_NATION_IRAQ = 964;

        //Ireland
        public int OLI_NATION_IRELAND = 353;

        //Israel
        public int OLI_NATION_ISRAEL = 972;

        //Italy
        public int OLI_NATION_ITALY = 39;

        //Ivory Coast, Republic Of
        public int OLI_NATION_IVORYCOAST = 225;

        //Jamaica
        public int OLI_NATION_JAMAICA = 876;

        //Japan
        public int OLI_NATION_JAPAN = 81;

        //Jordan
        public int OLI_NATION_JORDAN = 962;

        //Kazakhstan
        public int OLI_NATION_KAZAKHSTAN = 1005;

        //Kenya
        public int OLI_NATION_KENYA = 254;

        //Kiribati
        public int OLI_NATION_KIRIBATI = 686;

        //Korea
        public int OLI_NATION_KOREA = 82;

        //Korea, Democratic People's Republic of
        public int OLI_NATION_KOREADEMPEOPLEREP = 952;

        //Korea, Republic of
        public int OLI_NATION_KOREAREPUBLIC = 951;

        //Kuwait
        public int OLI_NATION_KUWAIT = 965;

        //Kyrgyzstan
        public int OLI_NATION_KYRGYZSTAN = 1006;

        //Lao People's Democratic Republic
        public int OLI_NATION_LAOS = 856;

        //Latvia
        public int OLI_NATION_LATVIA = 371;

        //Lebanon
        public int OLI_NATION_LEBANON = 961;

        //Lesotho
        public int OLI_NATION_LESOTHO = 266;

        //Liberia
        public int OLI_NATION_LIBERIA = 231;

        //Libyan Arab Jamahiriya
        public int OLI_NATION_LIBYA = 218;

        //Liechtenstein
        public int OLI_NATION_LIECHTENSTEIN = 423;

        //Lithuania
        public int OLI_NATION_LITHUANIA = 370;

        //Luxembourg
        public int OLI_NATION_LUXEMBOURG = 352;

        //Macao
        public int OLI_NATION_MACAO = 853;

        //Macedonia, The Former Yugoslav Republic of
        public int OLI_NATION_MACEDONIA = 389;

        //Madagascar
        public int OLI_NATION_MADAGASCAR = 261;

        //Malawi
        public int OLI_NATION_MALAWI = 265;

        //Malaysia
        public int OLI_NATION_MALAYSIA = 60;

        //Maldives
        public int OLI_NATION_MALDIVES = 1023;

        //Mali
        public int OLI_NATION_MALI = 223;

        //Malta
        public int OLI_NATION_MALTA = 356;

        //Marshall Islands
        public int OLI_NATION_MARSHALLISLANDS = 694;

        //Martinique
        public int OLI_NATION_MARTINIQUE = 596;

        //Mauritania
        public int OLI_NATION_MAURITANIA = 222;

        //Mauritius
        public int OLI_NATION_MAURITIUS = 230;

        //Mayotte
        public int OLI_NATION_MAYOTTEISLAND = 1017;

        //Mexico
        public int OLI_NATION_MEXICO = 52;

        //Micronesia, Federated States of
        public int OLI_NATION_MICRONESIA = 695;

        //Moldova, Republic of
        public int OLI_NATION_MOLDOVA = 373;

        //Monaco
        public int OLI_NATION_MONACO = 1028;

        //Mongolia
        public int OLI_NATION_MONGOLIA = 1008;

        //Montserrat
        public int OLI_NATION_MONTSERRAT = 664;

        //Morocco
        public int OLI_NATION_MOROCCO = 212;

        //Mozambique
        public int OLI_NATION_MOZAMBIQUE = 258;

        //Myanmar
        public int OLI_NATION_MYANMAR = 950;

        //Namibia
        public int OLI_NATION_NAMIBIA = 264;

        //Nauru
        public int OLI_NATION_NAURU = 674;

        //Nepal
        public int OLI_NATION_NEPAL = 977;

        //Netherlands
        public int OLI_NATION_NETHERLANDS = 31;

        //Netherlands Antilles
        public int OLI_NATION_NETHERLANDSANTILLES = 599;

        //New Caledonia
        public int OLI_NATION_NEWCALEDONIA = 687;

        //New Zealand
        public int OLI_NATION_NEWZEALAND = 64;

        //Nicaragua
        public int OLI_NATION_NICARAGUA = 505;

        //Niger
        public int OLI_NATION_NIGER = 227;

        //Nigeria
        public int OLI_NATION_NIGERIA = 234;

        //Niue
        public int OLI_NATION_NIUE = 683;

        //Norfolk Island
        public int OLI_NATION_NORFOLKISLAND = 1025;

        //Norway
        public int OLI_NATION_NORWAY = 47;

        //Oman
        public int OLI_NATION_OMAN = 968;

        //Other
        public int OLI_OTHER = 2147483647;

        //Pakistan
        public int OLI_NATION_PAKISTAN = 92;

        //Palau
        public int OLI_NATION_PALAU = 693;

        //Palestinian Territory, Occupied
        public int OLI_NATION_PALESTINE = 1032;

        //Panama (also known as Canal Zone)
        public int OLI_NATION_PANAMA = 1002;

        //Papua New Guinea
        public int OLI_NATION_PAPUANEWGUINEA = 675;

        //Paraguay
        public int OLI_NATION_PARAGUAY = 595;

        //Peru
        public int OLI_NATION_PERU = 51;

        //Philippines
        public int OLI_NATION_PHILIPPINES = 63;

        //Pitcairn
        public int OLI_NATION_PITCARINISLANDS = 1018;

        //Poland
        public int OLI_NATION_POLAND = 48;

        //Portugal
        public int OLI_NATION_PORTUGAL = 351;

        //Puerto Rico
        public int OLI_NATION_PUERTORICO = 510;

        //Qatar
        public int OLI_NATION_QATAR = 974;

        //Reunion
        public int OLI_NATION_REUNIONISLAND = 262;

        //Romania
        public int OLI_NATION_ROMANIA = 40;

        //Russian Federation
        public int OLI_NATION_RUSSIA = 7;

        //Rwanda
        public int OLI_NATION_RWANDA = 250;

        //Saint Helena
        public int OLI_NATION_STHELENA = 290;

        //Saint Kitts and Nevis
        public int OLI_NATION_STKITTSNEVIS = 869;

        //Saint Lucia
        public int OLI_NATION_STLUCIA = 758;

        //Saint Pierre and Miquelon
        public int OLI_NATION_STPIERRE = 508;

        //Saint Vincent and the Grenadines
        public int OLI_NATION_STVINCENT = 784;

        //Saipan
        public int OLI_NATION_SAIPAN = 670;

        //Samoa
        public int OLI_NATION_SAMOA = 1013;

        //San Marino
        public int OLI_NATION_SANMARINO = 378;

        //Sao Tome and Principe
        public int OLI_NATION_SAOTOME = 239;

        //Saudi Arabia
        public int OLI_NATION_SAUDIARABIA = 966;

        //Senegal
        public int OLI_NATION_SENEGAL = 221;

        //Seychelles
        public int OLI_NATION_SEYCHELLES = 1010;

        //Sierra Leone
        public int OLI_NATION_SIERRALEONE = 232;

        //Singapore
        public int OLI_NATION_SINGAPORE = 65;

        //Slovakia
        public int OLI_NATION_SLOVAKIA = 421;

        //Slovenia
        public int OLI_NATION_SLOVENIA = 386;

        //Solomon Islands
        public int OLI_NATION_SOLOMONISLANDS = 677;

        //Somalia
        public int OLI_NATION_SOMALIA = 252;

        //South Africa
        public int OLI_NATION_SOUTHAFRICA = 27;

        //South Georgia and the South Sandwich Islands
        public int OLI_NATION_SOUTHGEORGIASANDWICH = 512;

        //Spain
        public int OLI_NATION_SPAIN = 34;

        //Sri Lanka
        public int OLI_NATION_SRILANKA = 94;

        //Sudan
        public int OLI_NATION_SUDAN = 249;

        //Suriname
        public int OLI_NATION_SURINAME = 597;

        //Svalbard and Jan Mayen
        public int OLI_NATION_SVALBARDISLAND = 1019;

        //Swaziland
        public int OLI_NATION_SWAZILAND = 1031;

        //Sweden
        public int OLI_NATION_SWEDEN = 46;

        //Switzerland
        public int OLI_NATION_SWITZERLAND = 41;

        //Syrian Arab Republic
        public int OLI_NATION_SYRIA = 963;

        //Taiwan, Province of China
        public int OLI_NATION_TAIWAN = 886;

        //Tajikistan
        public int OLI_NATION_TAJIKISTAN = 992;

        //Tanzania, United Republic of
        public int OLI_NATION_TANZANIA = 255;

        //Thailand
        public int OLI_NATION_THAILAND = 66;

        //Timor-Leste
        public int OLI_NATION_EASTTIMOR = 1014;

        //Togo
        public int OLI_NATION_TOGO = 228;

        //Tokelau
        public int OLI_NATION_TOKELAU = 1020;

        //Tonga
        public int OLI_NATION_TONGAISLANDS = 676;

        //Trinidad and Tobago
        public int OLI_NATION_TRINIDADTOBAGO = 868;

        //Tunisia
        public int OLI_NATION_TUNISIA = 216;

        //Turkey
        public int OLI_NATION_TURKEY = 90;

        //Turkmenistan
        public int OLI_NATION_TURKMENISTAN = 993;

        //Turks and Caicos Islands
        public int OLI_NATION_TURKSCAICOSIS = 649;

        //Tuvalu
        public int OLI_NATION_TUVALU = 688;

        //Uganda
        public int OLI_NATION_UGANDA = 256;

        //Ukraine
        public int OLI_NATION_UKRAINE = 380;

        //United Arab Emirates
        public int OLI_NATION_UNITEDARABEMIRATES = 971;

        //United Kingdom
        public int OLI_NATION_UK = 44;

        //United States Minor Outlying Islands
        public int OLI_NATION_USMINOROUTLYINGISLANDS = 1021;

        //United States of America
        public int OLI_NATION_USA = 1;

        //Unknown
        public int OLI_UNKNOWN = 0;

        //Uruguay
        public int OLI_NATION_URUGUAY = 598;

        //Uzbekistan
        public int OLI_NATION_UZBEKISTAN = 998;

        //Vanuatu
        public int OLI_NATION_VANUATU = 678;

        //Venezuela
        public int OLI_NATION_VENEZUELA = 58;

        //Viet Nam
        public int OLI_NATION_VIETNAM = 84;

        //Virgin Islands, British
        public int OLI_NATION_BRITISHVIRGINIS = 284;

        //Virgin Islands, US
        public int OLI_NATION_VIRGINISLANDSUS = 511;

        //Wallis and Futuna
        public int OLI_NATION_WALLISISLANDS = 681;

        //Western Sahara
        public int OLI_NATION_WESTERNSARAHA = 1022;

        //Western Samoa
        public int OLI_NATION_WESTERNSAMOA = 685;

        //Yemen
        public int OLI_NATION_YEMEN = 967;

        //Yugoslavia
        public int OLI_NATION_YUGOSLAVIA = 38;

        //Zaire
        public int OLI_NATION_ZAIRE = 243;

        //Zambia
        public int OLI_NATION_ZAMBIA = 260;

        //Zimbabwe
        public int OLI_NATION_ZIMBABWE = 263;
    }

    /**
     * Operation for many ACORD objects. Used to request a phonetic search.   OLI_LU_OPERATION
     */
    public interface Operation
    {
        // Greater Than
        public int OLI_OP_GREATERTHAN = 4;

        // Greater than or equal to
        public int OLI_OP_GREATERTHANEQUALTO = 6;

        // Is equal to
        public int OLI_OP_EQUAL = 1;

        // Less Than
        public int OLI_OP_LESSTHAN = 3;

        // Less than or equal to
        public int OLI_OP_LESSTHANEQUALTO = 5;

        // Like.  Like and not like is read from the left, no wild cards allowed
        public int OLI_OP_LIKE = 7;

        // Not equal
        public int OLI_OP_NOTEQUAL = 2;

        // Not like.  Like and not like is read from the left, no wild cards allowed
        public int OLI_OP_NOTLIKE = 8;

        // Phonetic.  Used to request a phonetic search.
        public int OLI_OPERATION_PHONETIC = 10;

        // Similar to "Like" except allows searches for wildcards and sub-strings
        public int LOGICAL_OPERATOR_WILDCARDMATCH = 9;

        // Thesaurus.  Used to request a thesaurus search.
        public int OLI_OPERATION_THESAURUS = 11;
    }

    /**
     * Object type for many ACORD objects. OLI_LU_OBJECTTYPE
     */
    public interface ObjectType
    {
        //    AbdomenMeasure
        public int OLI_ABDOMENMEASURE = 121;

        //    AccountDesignationCC
        public int OLI_ACCOUNTDESIGNATIONCC = 323;

        //    Accounting Activity
        public int OLI_ACCOUNTINGACTIVITY = 164;

        //    AccountingStatement
        public int OLI_ACCOUNTINGSTATEMENT = 122;

        //    Activity
        public int OLI_ACTIVITY = 7;

        //    AdditionalInterestRateInfo
        public int OLI_ADDITIONALINTERESTRATEINFO = 312;

        //    Address
        public int OLI_ADDRESS = 2;

        //    AdminTransactionProduct
        public int OLI_ADMINTRANSACTIONPRODUCT = 309;

        //    AirSportsExp
        public int OLI_AIRSPORTSEXP = 74;

        //    Allocation
        public int OLI_ALLOCATION = 196;

        //    AllocTypeProduct
        public int OLI_ALLOCTYPEPRODUCT = 326;

        //    AllowedChange
        public int OLI_ALLOWEDCHANGE = 492;

        //    AllowedDayCC
        public int OLI_ALLOWEDDAYCC = 327;

        //    AllowedPercent
        public int OLI_ALLOWEDPERCENT = 493;

        //    AllowedRelationship
        public int OLI_ALLOWEDRELATIONSHIP = 123;

        //    AllowedSubstandard
        public int OLI_ALLOWEDSUBSTANDARD = 215;

        //    Alternate Premium Mode
        public int OLI_ALTPREMMODE = 107;

        //    AmountProduct
        public int OLI_AMOUNTPRODUCT = 217;

        //    AnnRider
        public int OLI_ANNRIDER = 86;

        //    Annuity
        public int OLI_ANNUITY = 25;

        //    AnnuityProduct
        public int OLI_ANNUITYPRODUCT = 185;

        //    AnnuityProductExclude
        public int OLI_ANNUITYPRODUCTEXCLUDE = 124;

        //    Annuity USA
        public int OLI_ANNUITYUSA = 88;

        //    ApplicationInfo
        public int OLI_APPLICATIONINFO = 58;

        //    ApptCounty
        public int OLI_APPTCOUNTY = 332;

        //    Arrangement
        public int OLI_ARRANGEMENT = 89;

        //    Arrangement Destination
        public int OLI_ARRDESTINATION = 91;

        //    Arrangement Source
        public int OLI_ARRSOURCE = 90;

        //    AssociatedResponseData
        public int OLI_ASSOCIATEDRESPONSEDATA = 335;

        //    Associated Response Data
        public int OLI_ASSOCRESPONSEDATA = 104;

        //    AssumedInterestRateCC
        public int OLI_ASSUMEDINTERESTRATECC = 336;

        //    Attachment
        public int OLI_ATTACHMENT = 9;

        //    AuthorInfo
        public int OLI_AUTHORINFO = 14;

        //    Authorization
        public int OLI_AUTHORIZATION = 188;

        //    AuthorizationEntityCC
        public int OLI_AUTHORIZATIONENTITYCC = 337;

        //    AuthorizationTransaction
        public int OLI_AUTHORIZATIONTRANSACTION = 130;

        //    AuthorizedSignatory
        public int OLI_AUTHORIZEDSIGNATORY = 222;

        //    AviationExp
        public int OLI_AVIATIONEXP = 81;

        //    Axis
        public int OLI_AXIS = 340;

        //    AxisDef
        public int OLI_AXISDEF = 341;

        //    BallooningExp
        public int OLI_BALLOONINGEXP = 75;

        //    Banking
        public int OLI_BANKING = 221;

        //    Benefit Limit
        public int OLI_BENEFITLIMIT = 166;

        //    BestDayToCallCC
        public int OLI_BESTDAYTOCALLCC = 343;

        //    BillingDetail
        public int OLI_BILLINGDETAIL = 344;

        //    Billing Detail
        public int OLI_BILLDETAIL = 97;

        //    BillingStatement
        public int OLI_BILLINGSTATEMENT = 345;

        //    Billing Statement
        public int OLI_BILLSTMT = 96;

        //    Breakpoint
        public int OLI_BREAKPOINT = 186;

        //    BusinessMethodCC
        public int OLI_BUSINESSMETHODCC = 346;

        //    BusinessProcessAllowed
        public int OLI_BUSINESSPROCESSALLOWED = 190;

        //    BusinessProcessCC
        public int OLI_BUSINESSPROCESSCC = 347;

        //    BusinessRuleContext
        public int OLI_BUSINESSRULECONTEXT = 52;

        //    BusinessRuleInfo
        public int OLI_BUSINESSRULEINFO = 15;

        //    BusinessRuleInfo
        public int OLI_BUSINESRULEINFOS = 50;

        //    Campaign
        public int OLI_CAMPAIGN = 235;

        //    CardiacMurmur
        public int OLI_CARDIACMURMER = 283;

        //    Carrier
        public int OLI_CARRIER = 36;

        //    CarrierAppointment
        public int OLI_CARRIERAPPOINTMENT = 11;

        //    CashValueRate
        public int OLI_CASHVALUERATE = 319;

        //    CauseOfDeath
        public int OLI_CAUSEOFDEATH = 321;

        //    ChangeSubType
        public int OLI_CHANGESUBTYPE = 348;

        //    ChargePctSchedule
        public int OLI_CHARGEPCTSCHEDULE = 131;

        //    ChestForcedMeasure
        public int OLI_CHESTFORCEDMEASURE = 174;

        //    ChestFullMeasure
        public int OLI_CHESTFULLMEASURE = 175;

        //    Claim
        public int OLI_CLAIM = 105;

        //    ClaimEstimate
        public int OLI_CLAIMESTIMATE = 311;

        //    ClaimMedConditionInfo
        public int OLI_CLAIMMEDCONDITIONINFO = 490;

        //    ClaimMedTreatmentInfo
        public int OLI_CLAIMMEDTREATMENTINFO = 497;

        //    Client
        public int OLI_CLIENT = 1;

        //    ClimbingExp
        public int OLI_CLIMBINGEXP = 79;

        //    COIRate
        public int OLI_COIRATE = 320;

        //    COLIndexCapCC
        public int OLI_COLINDEXCAPCC = 352;

        //    COLIndexCC
        public int OLI_COLINDEXCC = 353;

        //    CommFormula
        public int OLI_COMMFORMULA = 275;

        //    Commission Calc Activity
        public int OLI_COMMISSIONCALCACTIVITY = 200;

        //    CommissionCalcDetail
        public int OLI_COMMISSIONCALCDETAIL = 201;

        //    CommissionCalcInfo
        public int OLI_COMMISSIONCALCINFO = 202;

        //    CommissionDetail
        public int OLI_COMMISSIONDETAIL = 355;

        //    Commission Detail
        public int OLI_COMMDETAIL = 95;

        //    CommissionLinkCC
        public int OLI_COMMISSIONLINKCC = 356;

        //    CommissionProduct
        public int OLI_COMMISSIONPRODUCT = 203;

        //    CommissionStatement
        public int OLI_COMMISSIONSTATEMENT = 204;

        //    Commission Statement
        public int OLI_COMMSTMT = 94;

        //    CommOptionAvailable
        public int OLI_COMMOPTIONAVAILABLE = 176;

        //    CommRemittance
        public int OLI_COMMREMITTANCE = 272;

        //    CommSchedule
        public int OLI_COMMSCHEDULE = 274;

        //    CompensationPayment
        public int OLI_COMPENSATIONPAYMENT = 85;

        //    CompetitionDetail
        public int OLI_COMPETITIONDETAIL = 73;

        //    ComplexContentDescriptor
        public int OLI_COMPLEXCONTENTDESCRIPTOR = 496;

        //    ContentClassification
        public int OLI_CONTENTCLASSIFICATION = 358;

        //    ContingencyBenefitChange
        public int OLI_CONTINGENCYBENEFITCHANGE = 491;

        //    ContingentJointCC
        public int OLI_CONTINGENTJOINTCC = 359;

        //    Coverage
        public int OLI_COVERAGE = 360;

        //    Coverage Option
        public int OLI_COVOPTION = 21;

        //    Coverage Option Translation
        public int OLI_COVOPTIONXLAT = 110;

        //    Coverage Product
        public int OLI_COVERAGEPRODUCT = 117;

        //    Coverage Product Translation
        public int OLI_COVERAGEPRODUCTXLAT = 118;

        //    Coverage Translation
        public int OLI_COVERAGEXLAT = 111;

        //    Cov Option Product
        public int OLI_COVOPTIONPRODUCT = 154;

        //    CovOptionProductXLat
        public int OLI_COVOPTIONPRODUCTXLAT = 177;

        //    CrimConviction
        public int OLI_CRIMCONVICTION = 66;

        //    CriminalConviction
        public int OLI_CRIMINALCONVICTION = 368;

        //    Criteria
        public int OLI_CRITERIA = 40;

        //    CriteriaExpression
        public int OLI_CRITERIAEXPRESSION = 369;

        //    Currencies
        public int OLI_CURRENCIES = 87;

        //    Currency
        public int OLI_CURRENCY = 82;

        //    DataTransmittalSubType
        public int OLI_DATATRANSMITTALSUBTYPE = 370;

        //    DateCollection
        public int OLI_DATECOLLECTION = 489;

        //    DeathBenefitOptCC
        public int OLI_DEATHBENEFITOPTCC = 371;

        //    DefLifeInsMethodCC
        public int OLI_DEFLIFEINSMETHODCC = 372;

        //    Department
        // LOI_DEPARTMENT = 134;

        //    DestInvestProduct
        public int OLI_DESTINVESTPRODUCT = 236;

        //    DHParticipant
        public int OLI_DHPARTICIPANT = 28;

        //    DHRider
        public int OLI_DHRIDER = 30;

        //    DisabilityHealth
        public int OLI_DISABILITYHEALTH = 29;

        //    DistinguishedObject
        public int OLI_DISTINGUISHEDOBJECT = 316;

        //    DistributionAgreement
        public int OLI_DISTRIBUTATIONAGREEMENT = 271;

        //    DistributionAgreementInfo
        public int OLI_DISTRIBUTIONAGREEMENTINFO = 276;

        //    DistributionChannelCC
        public int OLI_DISTRIBUTIONCHANNELCC = 374;

        //    DistributionChannelInfo
        public int OLI_DISTRIBUTIONCHANNELINFO = 237;

        //    DistributionLevel
        public int OLI_DISTRIBUTIONLEVEL = 291;

        //    DivePurpose
        public int OLI_DIVEPURPOSE = 71;

        //    Dividend
        public int OLI_DIVIDEND = 211;

        //    DividendRate
        public int OLI_DIVIDENDRATE = 318;

        //    Education
        public int OLI_EDUCATION = 263;

        //    EmailAddress
        public int OLI_EMAILADDRESS = 56;

        //    Employment
        public int OLI_EMPLOYMENT = 136;

        //    Endorsement
        public int OLI_ENDORSEMENT = 180;

        //    EntityType
        public int OLI_ENTITYTYPE = 238;

        //    Error
        public int OLI_ERROR = 54;

        //    Errors
        public int OLI_ERRORS = 42;

        //    Event
        public int OLI_EVENT = 239;

        //    Exam
        public int OLI_EXAM = 133;

        //    ExchangeRate
        public int OLI_EXCHANGERATE = 83;

        //    Exclusion
        public int OLI_EXCLUSION = 181;

        //    ExclusionInvestProduct
        public int OLI_EXCLUSIONINVESTPRODUCT = 240;

        //    ExpenseNeed
        public int OLI_EXPENSENEED = 5;

        //    ExtendOrCall
        public int OLI_EXTENDORCALL = 241;

        //    Extension
        public int OLI_EXTENSION = 379;

        //    ExtensionContext
        public int OLI_EXTENSIONCONTEXT = 51;

        //    FamIllness
        public int OLI_FAMILLNESS = 64;

        //    FamilyIllness
        public int OLI_FAMILYILLNESS = 380;

        //    FeatureConflict
        public int OLI_FEATURECONFLICT = 220;

        //    FeatureOptConflict
        public int OLI_FEATUREOPTCONFLICT = 381;

        //    FeatureOptProduct
        public int OLI_FEATUREOPTPRODUCT = 218;

        //    FeatureOptRequisite
        public int OLI_FEATUREOPTREQUISITE = 382;

        //    FeatureProduct
        public int OLI_FEATUREPRODUCT = 191;

        //    FeatureProductInfo
        public int OLI_FEATUREPRODUCTINFO = 308;

        //    FeatureRequisite
        public int OLI_FEATUREREQUISITE = 219;

        //    FeatureTransactionProduct
        public int OLI_FEATURETRANSACTIONPRODUCT = 307;

        //    Fee
        public int OLI_FEE = 187;

        //    FinActivityTypeCC
        public int OLI_FINACTIVITYTYPECC = 383;

        //    FinancialActivity
        public int OLI_FINANCIALACTIVITY = 384;

        //    Financial Activity
        public int OLI_FINACTIVITY = 92;

        //    FinancialActivityInfo
        public int OLI_FINANCIALACTIVITYINFO = 242;

        //    Financial Experience
        public int OLI_FINANCIALEXPERIENCE = 138;

        //    FinancialStatement
        public int OLI_FINANCIALSTATEMENT = 387;

        //    Financial Statement
        public int OLI_FINSTMT = 93;

        //    ForeignTravel
        public int OLI_FOREIGNTRAVEL = 80;

        //    Form Instance
        public int OLI_FORMINSTANCE = 101;

        //    FormInstanceDestination
        public int OLI_FORMINSTANCEDESTINATION = 389;

        //    FormInstanceRequest
        public int OLI_FORMINSTANCEREQUEST = 390;

        //    Form Response
        // The values expected here depend on the form. In general, it is not necessary to have an object for the
        // response if the response is available elsewhere in the OLifE data. However, this can also be used to
        // indicate where in the OLifE model to find the data if it is not apparent.;
        public int OLI_FORMRESPONSE = 103;

        //    FreelookInvestRuleProduct
        public int OLI_FREELOOKINVESTRULEPRODUCT = 206;

        //    FundingSourceMethodsAllowed
        public int OLI_FUNDINGSOURCEMETHODSALLOWED = 277;

        //    GovtIDInfo
        public int OLI_GOVTIDINFO = 243;

        //    Grouping
        public int OLI_GROUPING = 16;

        //    HangglidingExp
        public int OLI_HANGGLIDINGEXP = 76;

        //    Height2
        public int OLI_HEIGHT2 = 244;

        //    HHFamilyIns
        public int OLI_HHFAMILYINS = 68;

        //    HHFamilyInsurance
        public int OLI_HHFAMILYINSURANCE = 394;

        //    Holding
        public int OLI_HOLDING = 4;

        //    Holding Translation
        public int OLI_HOLDINGXLAT = 109;

        //    Household
        public int OLI_HOUSEHOLD = 17;

        //    Identification
        public int OLI_IDENTIFICATION = 302;

        //    IdentityVerification
        public int OLI_IDENTITYVERIFICATION = 301;

        //    IllustrationReportRequest
        public int OLI_ILLUSTRATIONREPORTREQUEST = 223;

        //    IllustrationRequest
        public int OLI_ILLUSTRATIONREQUEST = 396;

        //    IllustrationResult
        public int OLI_ILLUSTRATIONRESULT = 397;

        //    IllustrationTxn
        public int  OLI_ILLUSTRATIONTXN = 398;

        //    IncomeOptConflict
        public int OLI_INCOMEOPTCONFLICT = 245;

        //    IncomeOptionCC
        public int OLI_INCOMEOPTIONCC = 400;

        //    IncomeOptionInfo
        public int OLI_INCOMEOPTINFO = 246;

        //    IncomeOptionInfo
        public int OLI_INCOMEOPTIONINFO = 401;

        //    IncomeOptRequisite
        public int OLI_INCOMEOPTREQUISITE = 247;

        //    IncomePayoutProductOption
        public int OLI_INCOMEPAYOUTPRODUCTOPTION = 192;

        //    InformationService
        public int OLI_INFORMATIONSERVICE = 248;

        //    Intent
        public int OLI_INTENT = 119;

        //    Investment
        public int OLI_INVESTMENT = 31;

        //    Investment Portfolio Translation
        public int OLI_INVESTPORTFOLIOXLAT = 113;

        //    Investment Product Translation
        public int OLI_INVESTPRODUCTXLAT = 108;

        //    InvestmentSubTotalInfo
        public int OLI_INVESTSUBTOTALINFO = 249;

        //    InvestmentSubTotalInfo
        public int OLI_INVESTMENTSUBTOTALINFO = 404;

        //    InvestPortfolio
        public int OLI_INVESTPORTFOLIO = 34;

        //    InvestProduct
        public int OLI_INVESTPRODUCT = 33;

        //    InvestProductInfo
        public int OLI_INVESTPRODUCTINFO = 84;

        //    InvestProductInfoXLat
        public int OLI_INVESTPRODUCTINFOXLAT = 250;

        //    InvestProductRateVariation
        public int OLI_INVESTPRODUCTRATEVARIATION = 193;

        //    InvestProductRateVariationByDuration
        public int OLI_INVESTPRODUCTRATEVARIATIONBYDURATION = 194;

        //    InvestProductRateVariationByVolume
        public int OLI_INVESTPRODUCTRATEVARIATIONBYVOLUME = 195;

        //    InvestRuleProduct
        public int OLI_INVESTRULEPRODUCT = 207;

        //    IRSPremCalcMethod
        public int OLI_IRSPREMCALCMETHOD = 305;

        //    IssueGenderCC
        public int OLI_ISSUEGENDERCC = 408;

        //    JurisdictionApproval
        public int OLI_JURISDICTIONAPPROVAL = 251;

        //    JurisdictionCC
        public int OLI_JURISDICTIONCC = 410;

        //    Key
        public int OLI_KEY = 411;

        //    KeyDef
        public int OLI_KEYDEF = 412;

        //    KeyedValue
        public int OLI_KEYEDVALUE = 23;

        //    Kit
        public int OLI_KIT = 234;

        //    LabTesting
        public int OLI_LABTESTING = 226;

        //    LabTestRemark
        public int OLI_LABTESTREMARK = 228;

        //    LabTestResult
        public int OLI_LABTESTRESULT = 227;

        //    LapseProvision
        public int OLI_LAPSEPROVISION = 212;

        //    LevelizationOptionCC
        public int OLI_LEVELIZATIONOPTIONCC = 413;

        //    License
        public int OLI_LICENSE = 10;

        //    Life
        public int OLI_LIFE = 19;

        //    LifeCoverage
        public int OLI_LIFECOVERAGE = 20;

        //    LifeParticipant
        public int OLI_LIFEPARTICIPANT = 22;

        //    Life Product
        public int OLI_LIFEPRODUCT = 162;

        //    LifestyleActivity
        public int OLI_LIFESTYLEACTIVITY = 69;

        //    LifeStyleViolation
        public int OLI_LIFESTYLEVIOLATION = 488;

        //    LifeUSA
        public int OLI_LIFEUSA = 24;

        //    LifeUSAProduct
        public int OLI_LIFEUSAPRODUCT = 304;

        //    LineOfAuthority
        public int OLI_LINEOFAUTHORITY = 266;

        //    LineOfAuthorityCC
        public int OLI_LINEOFAUTHORITYCC = 415;

        //    LinkInfo
        public int OLI_LINKINFO = 53;

        //    LinkManager
        public int OLI_LINKMANAGER = 41;

        //    Loan
        public int OLI_LOAN = 106;

        //    LoanIntMethodCC
        public int OLI_LOANINTMETHODCC = 416;

        //    LoanProvision
        public int OLI_LOANPROVISION = 209;

        //    LogicalCriteria
        public int OLI_LOGICALCRITERIA = 487;

        //    LogicalExpression
        public int OLI_LOGICALEXPRESSION = 486;

        //    LogonInfoDialog
        public int COMMON_LOGONINFODIALOG = 49;

        //    LostCapability
        public int OLI_LOSTCAPABILITY = 495;

        //    Market
        public int OLI_MARKET = 306;

        //    MedCondition
        public int OLI_MEDCONDITION = 61;

        //    MedicalCondition
        public int OLI_MEDICALCONDITION = 417;

        //    MedicalEquipment
        public int OLI_MEDICALEQUIPMENT = 282;

        //    MedicalExam
        public int OLI_MEDICALEXAM = 418;

        //    Medical Exam
        public int OLI_MEDEXAM = 100;

        //    MedicalPrevention
        public int OLI_MEDICALPREVENTION = 419;

        //    MedicalTreatment
        public int OLI_MEDICALTREATMENT = 420;

        //    MedPrevention
        public int OLI_MEDPREVENTION = 63;

        //    MedTreatment
        public int OLI_MEDTREATMENT = 62;

        //    MetaData
        public int OLI_METADATA = 421;

        //    MIBRequest
        public int OLI_MIBREQUEST = 422;

        //    MIBServiceDescriptor
        public int OLI_MIBSERVICEDESCRIPTOR = 423;

        //    MIBServiceOptions
        public int OLI_MIBSERVICEOPTIONS = 424;

        //    MilitaryExp
        public int OLI_MILITARYEXP = 67;

        //    MinBalanceCalcRule
        public int OLI_MINBALANCECALCRULE = 210;

        //    NonForProvision
        public int OLI_NONFORPROVISION = 213;

        //    NumInvestProduct
        public int OLI_NUMINVESTPRODUCT = 208;

        //    ObjCollection
        public int OLI_OBJCOLLECTION = 44;

        //    ObjectTypeCC
        public int OLI_OBJECTTYPECC = 425;

        //    OLIfE
        public int OLI_OLIFE = 426;

        //    OLIfEExtension
        public int OLI_OLIFEEXTENSION = 252;

        //    Organization
        public int OLI_ORGANIZATION = 116;

        //    OrganizationFinancialData
        public int OLI_ORGANIZATIONFINANCIALDATA = 173;

        //    Other
        public int OLI_OTHER = 2147483647;

        //    Ownership
        public int OLI_OWNERSHIP = 189;

        //    ParachutingExp
        public int OLI_PARACHUTINGEXP = 78;

        //    Participant
        public int OLI_PARTICIPANT = 428;

        //    Party
        public int OLI_PARTY = 6;

        //    Party Translation
        public int OLI_PARTYXLAT = 114;

        //    Payment
        public int OLI_PAYMENT = 163;

        //    PaymentFormCC
        public int OLI_PAYMENTFORMCC = 430;

        //    Payment Mode Method
        public int OLI_PAYMENTMETHOD = 141;

        //    PaymentModeMethProduct
        public int OLI_PAYMENTMODEMETHPRODUCT = 431;

        //    PaymentSourceCC
        public int OLI_PAYMENTSOURCECC = 432;

        //    Payout
        public int OLI_PAYOUT = 26;

        //    PayoutChange
        public int OLI_PAYOUTCHANGE = 485;

        //    PayoutParticipant
        public int OLI_PAYOUTPARTICIPANT = 27;

        //    PeriodCertainCC
        public int OLI_PERIODCERTAINCC = 433;

        //    PeriodicBalanceYTDInfo
        public int OLI_PERIODICBALANCEYTDINFO = 253;

        //    Person
        public int OLI_PERSON = 115;

        //    Pharmacy
        public int OLI_PHARMACY = 168;

        //    Phone
        public int OLI_PHONE = 3;

        //    Physician
        public int OLI_PHYSICIAN = 169;

        //    PhysicianInfo
        public int OLI_PHYSICIANINFO = 170;

        //    Policy
        public int OLI_POLICY = 18;

        //    PolicyActivityList
        public int OLI_POLICYACTIVITYLIST = 254;

        //    PolicyIssueTypeCC
        public int OLI_POLICYISSUETYPECC = 436;

        //    PolicyProduct
        public int OLI_POLICYPRODUCT = 35;

        //    Policy Product Info
        public int OLI_POLICYPRODUCTINFO = 140;

        //    Policy Product Translation
        public int OLI_POLICYPRODUCTXLAT = 112;

        //    PolicyStatusCC
        public int OLI_POLICYSTATUSCC = 310;

        //    PolicyValues
        public int OLI_POLICYVALUES = 494;

        //    PreferredReqFulfiller
        public int OLI_PREFERREDREQFULFILLER = 224;

        //    PremiumRate
        public int OLI_PREMIUMRATE = 317;

        //    PrescriptionDrug
        public int OLI_PRESCRIPTIONDRUG = 171;

        //    PrescriptionFill
        public int OLI_PRESCRIPTIONFILL = 172;

        //    PriorLTC
        public int OLI_PRIORLTC = 167;

        //    PriorName
        public int OLI_PRIORNAME = 55;

        //    Producer
        public int OLI_PRODUCER = 13;

        //    ProducerAgreement
        public int OLI_PRODUCERAGREEMENT = 255;

        //    ProductConflict
        public int OLI_PRODUCTCONFLICT = 198;

        //    ProductRequisite
        public int OLI_PRODUCTREQUISITE = 197;

        //    ProductTypeInfo
        public int OLI_PRODUCTTYPEINFO = 315;

        //    PropertyandCasualty
        public int OLI_PROPERTYANDCASUALTY = 268;

        //    ProxyVendor
        public int OLI_PROXYVENDOR = 440;

        //    QualifiedPlanCC
        public int OLI_QUALIFIEDPLANCC = 441;

        //    QualifiedPlanEntity
        public int OLI_QUALIFIEDPLANENTITY = 256;

        //    QualitativeResult
        public int OLI_QUALITATIVERESULT = 229;

        //    QuantitativeResult
        public int OLI_QUANTITATIVERESULT = 230;

        //    RacingExp
        public int OLI_RACINGEXP = 72;

        //    RateOfReturnInfo
        public int OLI_RATEOFRETURNINFO = 484;

        //    RateVariation
        public int OLI_RATEVARIATION = 443;

        //    RateVariationByDuration
        public int OLI_RATEVARIATIONBYDURATION = 444;

        //    RateVariationByVolume
        public int OLI_RATEVARIATIONBYVOLUME = 445;

        //    RatingAgencyInfo
        public int OLI_RATINGAGENCYINFO = 216;

        //    RecordOwner
        public int OLI_RECORDOWNER = 43;

        //    ReferenceRange
        public int OLI_REFERENCERANGE = 231;

        //    Registration
        public int OLI_REGISTRATION = 264;

        //    RegistrationJurisdiction
        public int OLI_REGISTRATIONJURISDICTION = 257;

        //    RegulatoryDistAgreement
        public int OLI_REGULATORYDISTAGREEMENT = 314;

        //    Reinsurance Info
        public int OLI_REINSURANCEINFO = 165;

        //    ReinsuranceRequest
        public int OLI_REINSURANCEREQUEST = 448;

        //    Relation
        public int OLI_RELATION = 8;

        //    RelationshipCC
        public int OLI_RELATIONSHIPCC = 449;

        //    RequestBasis
        public int OLI_REQUESTBASIS = 450;

        //    RequirementInfo
        public int OLI_REQUIREMENTINFO = 57;

        //    RestrictionInfo
        public int OLI_RESTRICTIONINFO = 205;

        //    ResultBasis
        public int OLI_RESULTBASIS = 451;

        //    ResultInfo
        public int OLI_RESULTINFO = 452;

        //    ResultSet
        public int OLI_RESULTSET = 37;

        //    ResultsReceiverParty
        public int OLI_RESULTSRECEIVERPARTY = 258;

        //    Reward
        public int OLI_REWARD = 214;

        //    Rider
        public int OLI_RIDER = 454;

        //    Risk
        public int OLI_RISK = 59;

        //    RSField
        public int OLI_RSFIELD = 38;

        //    RSFields
        public int OLI_RSFIELDS = 39;

        //    Scenario
        public int OLI_SCENARIO = 98;

        //    ScenarioParticipant
        public int OLI_SCENARIOPARTICIPANT = 455;

        //    Scenario Participant
        public int OLI_SCENARIOPART = 99;

        //    ScheduledChange
        public int OLI_SCHEDULEDCHANGE = 281;

        //    ScheduledPaymentCC
        public int OLI_SCHEDULEDPAYMENTCC = 456;

        //    Security
        public int OLI_SECURITY = 12;

        //    SelectActivityDialog
        public int COMMON_SELECTACTIVITYDIALOG = 48;

        //    SelectHoldingDialog
        public int COMMON_SELECTHOLDINGDIALOG = 47;

        //    SelectPartyDialog
        public int COMMON_SELECTPARTYDIALOG = 46;

        //    Server
        public int OLI_SERVER = 45;

        //    SettlementDetail
        public int OLI_SETTLEMENTDETAIL = 184;

        //    SettlementInfo
        public int OLI_SETTLEMENTINFO = 183;

        //    Signature Information
        public int OLI_SIGNATUREINFO = 102;

        //    SitusInfo
        public int OLI_SITUSINFO = 313;

        //    Source Info Aggregate
        public int OLI_SOURCEINFO = 199;

        //    SourceInvestProduct
        public int OLI_SOURCEINVESTPRODUCT = 259;

        //    SourceOfFundsCC
        public int OLI_SOURCEOFFUNDSCC = 460;

        //    SpecialTestOrdered
        public int OLI_SPECIALTESTORDERED = 232;

        //    SplitPctIncrementsCC
        public int OLI_SPLITPCTINCREMENTSCC = 461;

        //    StatusEvent
        public int OLI_OBJ_STATUSEVENT = 120;

        //    StatusReceiverParty
        public int OLI_STATUSRECEIVERPARTY = 462;

        //    SubAccount
        public int OLI_SUBACCOUNT = 32;

        //    SubstanceUsage
        public int OLI_SUBSTANCEUSAGE = 60;

        //    SubstandardRating
        public int OLI_SUBSTANDARDRATING = 182;

        //    Superannuation
        public int OLI_SUPPERANNUATION = 260;

        //    Superannuation
        public int OLI_SUPERANNUATION = 463;

        //    SurrenderChargeSchedule
        public int OLI_SURRENDERCHARGESCHEDULE = 261;

        //    SystemMessage
        public int OLI_SYSTEMMESSAGE = 179;

        //    Table
        public int OLI_TABLE = 465;

        //    TableRef
        public int OLI_TABLEREF = 300;

        //    TargetURL
        public int OLI_TARGETURL = 466;

        //    TaxWithholding
        public int OLI_TAXWITHHOLDING = 178;

        //    TrackingInfo
        public int OLI_TRACKINGINFO = 225;

        //    TransResult
        public int OLI_TRANSRESULT = 467;

        //    TrustTypeCC
        public int OLI_TRUSTTYPECC = 468;

        //    TXLife
        public int OLI_TXLIFE = 469;

        //    TXLifeNotify
        public int OLI_TXLIFENOTIFY = 470;

        //    TXLifeRequest
        public int OLI_TXLIFEREQUEST = 471;

        //    TXLifeResponse
        public int OLI_TXLIFERESPONSE = 472;

        //    UltraliteExp
        public int OLI_ULTRALITEEXP = 77;

        //    UnderwaterDivingExp
        public int OLI_UNDERWATERDIVINGEXP = 70;

        //    UnderwritingClassProduct
        public int OLI_UNDERWRITINGCLASSPRODUCT = 156;

        //    UnderwritingClassProductXLat
        public int OLI_UNDERWRITINGCLASSPRODUCTXLAT = 265;

        //    Unknown object = 1001
        public int OLI_OBJ_1001 = 1001;

        //    Unknown object = 270
        public int OLI_OBJ_270 = 270;

        //    Unknown object = 273
        public int OLI_OBJ_273 = 273;

        //    Unknown object = 278
        public int OLI_OBJ_278 = 278;

        //    Unknown object = 279
        public int OLI_OBJ_279 = 279;

        //    Unknown object = 280
        public int OLI_OBJ_280 = 280;

        //    Unknown object = 284
        public int OLI_OBJ_284 = 284;

        //    Unknown object = 285
        public int OLI_OBJ_285 = 285;

        //    Unknown object = 286
        public int OLI_OBJ_286 = 286;

        //    Unknown object = 287
        public int OLI_OBJ_287 = 287;

        //    Unknown object = 288
        public int OLI_OBJ_288 = 288;

        //    Unknown object = 289
        public int OLI_OBJ_289 = 289;

        //    Unknown object = 292
        public int OLI_OBJ_292 = 292;

        //    Unknown object = 293
        public int OLI_OBJ_293 = 293;

        //    Unknown object = 294
        public int OLI_OBJ_294 = 294;

        //    Unknown object = 295
        public int OLI_OBJ_295 = 295;

        //    Unknown object = 296
        public int OLI_OBJ_296 = 296;

        //    Unknown object = 297
        public int OLI_OBJ_297 = 297;

        //    Unknown object = 298
        public int OLI_OBJ_298 = 298;

        //    Unknown object = 299
        public int OLI_OBJ_299 = 299;

        //    UrineTemperature
        public int OLI_URINETEMPERATURE = 233;

        //    URL
        public int OLI_URL = 290;

        //    UserAuthRequest
        public int OLI_USERAUTHREQUEST = 474;

        //    UserAuthResponse
        public int OLI_USERAUTHRESPONSE = 475;

        //    UserPswd
        public int OLI_USERPSWD = 476;

        //    Values
        public int OLI_VALUES = 267;

        //    Vector
        public int OLI_VECTOR = 478;

        //    VectorItem
        public int OLI_VECTORITEM = 479;

        //    VectorRequest
        public int OLI_VECTORREQUEST = 480;

        //    VendorApp
        public int OLI_VENDORAPP = 481;

        //    VerifierParticipant
        public int OLI_VERIFIERPARTICIPANT = 303;

        //    Violation
        public int OLI_VIOLATION = 65;

        //    Weight2
        public int OLI_WEIGHT2 = 269;

        //    XTbML aggregate defined by XTbML schema
        public int OLI_XTBML = 132;

        //    XTbML AxisDef
        public int XTBML_AXISDEF = 128;

        //    XTbML Content Classification
        public int XTBML_CONTENTCLASSIFICATION = 125;

        //    XTbML MetaData
        public int XTBML_METADATA = 126;

        //    XTbML MetaData
        public int XTBML_KEYDEF = 127;
    }
}
