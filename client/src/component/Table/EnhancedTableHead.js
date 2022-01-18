import * as React from "react";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import Checkbox from "@mui/material/Checkbox";
import TableSortLabel from "@mui/material/TableSortLabel";
import Box from "@mui/material/Box";
import {visuallyHidden} from "@mui/utils";
import PropTypes from "prop-types";

export default function EnhancedTableHead(props) {
    let headCells = [];
    const { onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort, rows, keys } =
        props;
    const createSortHandler = (property) => (event) => {
        onRequestSort(event, property);
    };

    const createHeadCell = (id, numeric, disablePadding, label) => {
        if (!isNaN(rows[0].body[id])) {
            numeric = true;
        }

        return {
            id,
            numeric,
            disablePadding,
            label,
        };
    };

    const pushHeadCells = () => {
        for (let i = 0; i < keys.length; i++) {
            headCells[i] = createHeadCell(keys[i], false, false, keys[i]);
        }
    };
    pushHeadCells();

    return (
        <TableHead>
            <TableRow>
                <TableCell />
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        indeterminate={numSelected > 0 && numSelected < rowCount}
                        checked={rowCount > 0 && numSelected === rowCount}
                        onChange={onSelectAllClick}
                        inputProps={{
                            'aria-label': 'select all desserts',
                        }}
                    />
                </TableCell>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        // align={headCell.numeric ? 'right' : 'left'}
                        align={headCell.id === 'anonymized_id' || headCell.id === 'patientId' ? 'left' : 'right'}
                        padding={headCell.disablePadding ? 'none' : 'normal'}
                        sortDirection={orderBy === headCell.id ? order : false}
                    >
                        <TableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : 'asc'}
                            onClick={createSortHandler(headCell.id)}
                        >
                            {headCell.label}
                            {orderBy === headCell.id ? (
                                <Box component="span" sx={visuallyHidden}>
                                    {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                </Box>
                            ) : null}
                        </TableSortLabel>
                    </TableCell>
                ))}
            </TableRow>
        </TableHead>
    );
}

EnhancedTableHead.propTypes = {
    numSelected: PropTypes.number.isRequired,
    onRequestSort: PropTypes.func.isRequired,
    onSelectAllClick: PropTypes.func.isRequired,
    order: PropTypes.oneOf(['asc', 'desc']).isRequired,
    orderBy: PropTypes.string.isRequired,
    rowCount: PropTypes.number.isRequired,
    rows: PropTypes.array.isRequired,
    keys: PropTypes.array.isRequired,
};