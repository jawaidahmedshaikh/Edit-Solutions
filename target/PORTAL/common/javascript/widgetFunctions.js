function move(tableId, transaction, action, target)
{
    if (!verifyIfRowsExist(tableId))
    {
        alert('No rows to move.');
        return;
    }

    var selectedRowIds = getSelectedRowIds(tableId);

    if (selectedRowIds.length == 0)
    {
        alert('Please Select row/rows to be added.');
        return;
    }

    var selectedIdsElement = document.getElementById("selectedIds");

    selectedIdsElement.value = selectedRowIds;

    sendTransactionAction(transaction, action, target);
}

/*
 * Finds if any rows exist in the table with 'tableId'.
 */
function verifyIfRowsExist(tableId)
{
    var isExists = true;

    var tTable = document.getElementById(tableId);

    if (tTable.rows.length == 0)
    {
        isExists = false;
    }

    return isExists;
}

function onSingleClick(tableId)
{        
    try
    {
        var selectedRowIds = document.getElementById("selectedRowIds_" + tableId);        

        selectedRowIds.value = getSelectedRowIds(tableId);

        onTableRowSingleClick(tableId);
    }
    catch (e)
    {
    }
}

/**
 * Editable TableModels that render a <select> automatically invoke
 * this method which propagates to the "onTableRowSelectChange(tableId)" callback
 * method if it exists.
 */
function onSelectChange(selectId)
{
    try
    {
        onTableRowSelectChange(selectId);
    }
    catch (e)
    {
    }
}

function onDoubleClick(tableId)
{
    try
    {
        var selectedRowIds = document.getElementById("selectedRowIds_" + tableId);

        selectedRowIds.value = getSelectedRowIds(tableId);

        onTableRowDoubleClick(tableId);
    }
    catch (e)
    {
    }
}
