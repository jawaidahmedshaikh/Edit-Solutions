/*
 * User: gfrosti
 * Date: May 24, 2006
 * Time: 12:37:03 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.TableRow;

import edit.services.db.hibernate.SessionHelper;

import fission.utility.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logging.LogColumn;

import org.hibernate.metadata.ClassMetadata;

public class LogColumnTableRow extends TableRow
{
    private String columnName;

    private String columnLabel;

    private String columnDescription;

    private String sequence;

    private Long logColumnPK;

    public LogColumnTableRow(LogColumn logColumn)
    {
        this.columnName = logColumn.getColumnName();

        this.columnLabel = logColumn.getColumnLabel();

        this.columnDescription = logColumn.getColumnDescription();

        this.sequence = String.valueOf(logColumn.getSequence());

        this.logColumnPK = logColumn.getLogColumnPK();
    }

    /**
     * Convenience constructor to build a candidate dummy LogColumnTableRow.
     *
     * @param columnName
     * @param columnLabel
     * @param columnDescription
     * @param sequence
     */
    public LogColumnTableRow(String columnName, String columnLabel, String columnDescription, String sequence)
    {
        this.columnName = columnName;

        this.columnLabel = columnLabel;

        this.columnDescription = columnDescription;

        this.sequence = sequence;

        this.logColumnPK = DUMMY_TABLEROW_PK;
    }

    public String getRowId()
    {
        return logColumnPK.toString();
    }

    /**
     * Maps the current values to the TableModel's cell values Map.
     */
    public void populateCellValues()
    {
        if (isSelected())
        {
            getCellValues().put(LogColumnTableModel.COLUMN_COLUMNNAME + "S", getColumnNames());

            getCellValues().put(LogColumnTableModel.COLUMN_COLUMNLABEL + "S", getColumnLabels());

            getCellValues().put(LogColumnTableModel.COLUMN_COLUMNDESCRIPTION + "S", getColumnDescriptions());
        }

        getCellValues().put(LogColumnTableModel.COLUMN_COLUMNNAME, getColumnName());

        getCellValues().put(LogColumnTableModel.COLUMN_COLUMNLABEL, getColumnLabel());

        getCellValues().put(LogColumnTableModel.COLUMN_COLUMNDESCRIPTION, getColumnDescription());

        getCellValues().put(LogColumnTableModel.COLUMN_SEQUENCE, getSequence());
    }

    public String getColumnName()
    {
        return columnName;
    }

    public String getColumnLabel()
    {
        return columnLabel;
    }

    public String getColumnDescription()
    {
        return columnDescription;
    }

    public String getSequence()
    {
        return sequence;
    }

    /**
     * The list of all Table Name(s) that have been mapped to Hibernate Entities
     * sorted ascending.
     *
     * @return
     */
    private List getColumnNames()
    {
        List sortedTableNames = new ArrayList();

        ClassMetadata[] classMetadatas = SessionHelper.getClassMetadata(SessionHelper.EDITSOLUTIONS);

        for (int i = 0; i < classMetadatas.length; i++)
        {
            ClassMetadata classMetadata = classMetadatas[i];

            String entityName = Util.getClassName(classMetadata.getEntityName());

            sortedTableNames.add(entityName.toUpperCase());
        }

        Collections.sort(sortedTableNames);

//        addExternalDBTableEntries(sortedTableNames);

        return new ArrayList(sortedTableNames);
    }

    /**
     * The list of all Column Label(s) for the current DbTableName.
     *
     * @return
     */
    private List getColumnLabels()
    {
        List sortedColumnNames = new ArrayList();

        ClassMetadata dbTableClassMetadata = getDbTableClassMetadata();

        if (dbTableClassMetadata != null)
        {
            String[] propertyNames = dbTableClassMetadata.getPropertyNames();

            for (int i = 0; i < propertyNames.length; i++)
            {
                String propertyName = propertyNames[i];

                sortedColumnNames.add(propertyName.toUpperCase());
            }
        }

        Collections.sort(sortedColumnNames);

//        addExternalDBColumnEntries(sortedColumnNames);

        return new ArrayList(sortedColumnNames);
    }

    private List getColumnDescriptions()
    {
        //  This method and getColumnNames and getColumnLabels should go away since we are no longer using db tables
        //  and db columns.  Just putting this here for now to allow compile and figure out what gui is doing

        return new ArrayList();
    }

    /**
     * Finds the associated ClassMetadata for the current dbTableName of this TableRow.
     *
     * @return
     */
    private ClassMetadata getDbTableClassMetadata()
    {
        ClassMetadata classMetadata = null;

        String dbTableName = getColumnName();

        ClassMetadata[] classMetadatas = SessionHelper.getClassMetadata(SessionHelper.EDITSOLUTIONS);

        for (int i = 0; i < classMetadatas.length; i++)
        {
            ClassMetadata currentClassMetadata = classMetadatas[i];

            String entityName = Util.getClassName(currentClassMetadata.getEntityName()).toUpperCase();

            if (entityName.equals(dbTableName))
            {
                classMetadata = currentClassMetadata;

                break;
            }
        }

        return classMetadata;
    }

    /**
     * Business users require logging fields that are outside the scope of DBTableNames.
     * It is expected that these extra fields will be left to the DBColumnNames, so the
     * user should just select ( NONE ).
     *
     * @param tableNames
     */
//    private void addExternalDBTableEntries(List tableNames)
//    {
//        tableNames.add(0, "* " + LogColumn.SPECIAL_COLUMN_NONE + " *");
//    }

    /**
     * Business users require logging fields that are outside the scope of DBTableNames.
     * It is expected that these extra fields will be left to the DBColumnNames, so the
     * user should just select ( NONE ), while the DBColumn will offer additional these
     * additional choices.
     *
     * @param columnNames
     */
//    private void addExternalDBColumnEntries(List columnNames)
//    {
//        String[] specialColumns = LogColumn.SPECIAL_COLUMNS;
//
//        for (int i = 0; i < specialColumns.length; i++)
//        {
//            String specialColumn = specialColumns[i];
//
//            columnNames.add(i, "* " + specialColumn + " *");
//        }
//    }
}
