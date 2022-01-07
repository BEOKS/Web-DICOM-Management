import * as React from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import StudyTable from './StudyTable';

export default function DicomRow(props) {
    const [open, setOpen] = React.useState(false);
    
    const isItemSelected = props.isItemSelected;
    const labelId = props.labelId;
    const handleClick = props.handleClick;
    const row = props.row;
    const keys = props.keys;

    const createTableCell = (row) => {
        const elements = [];
        for (let i = 1; i < keys.length; i++) {
            elements[i - 1] = <TableCell align="right" key={keys[i]}>{row[keys[i]]}</TableCell>;
        }
        return elements;
    };

    return (
        <React.Fragment>
            <TableRow
                hover
                onClick={(event) => handleClick(event, row[keys[0]])}
                role="checkbox"
                aria-checked={isItemSelected}
                tabIndex={-1}
                key={row[keys[0]]}
                selected={isItemSelected}
            >
                <TableCell>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={(event) => {
                            event.stopPropagation();
                            setOpen(!open);
                            }}
                    >
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        checked={isItemSelected}
                        inputProps={{
                            'aria-labelledby': labelId,
                        }}
                    />
                </TableCell>
                <TableCell
                    component="th"
                    id={labelId}
                    scope="row"
                    padding="none"
                >
                    {row[keys[0]]}
                </TableCell>
                {createTableCell(row)}
            </TableRow>
            <StudyTable open={open} colSpan={keys.length + 2}/>
        </React.Fragment>
    );
}