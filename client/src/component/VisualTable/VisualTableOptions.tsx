import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../store';
import { VisualTableAction } from './VisualTableReducer';
import { ToggleButton, ToggleButtonGroup, Stack, Alert } from '@mui/material';

type VisualTableOptionsProps = {
    keys: string[]
};

const VisualTableOptions: React.FC<VisualTableOptionsProps> = ({ keys }) => {
    const dispatch = useDispatch();
    const options = useSelector((state: RootState) => state.VisualTableReducer.options);

    const handleFormat = (
        event: React.MouseEvent<HTMLElement>,
        newOptions: string[],
    ) => {
        dispatch(VisualTableAction.setOptions(newOptions));
    };

    return (
        <Stack spacing={2}>
            <Alert variant="outlined" severity="info">
                Please select the options to see the graph.
            </Alert>
            <ToggleButtonGroup
                value={options}
                onChange={handleFormat}
                aria-label="text formatting"
                size="small"
                color="info"
                fullWidth
            >
                {keys.map(key => <ToggleButton value={key} aria-label={key} key={key}>{key}</ToggleButton>)}
            </ToggleButtonGroup>
        </Stack>
    );
}

export default VisualTableOptions;