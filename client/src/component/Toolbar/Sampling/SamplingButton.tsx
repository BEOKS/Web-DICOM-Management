import { Button } from '@mui/material';
import { useDispatch } from 'react-redux';
import { SamplingAction } from './SamplingReducer';
import SamplingDialog from './SamplingDialog';

export default function SamplingButton() {
    const dispatch = useDispatch();

    const handleOnClick = () => {
        dispatch(SamplingAction.openDialog());
    };

    return (
        <div>
            <Button onClick={handleOnClick} variant="outlined" sx={{ ml: 1 }}>Sampling</Button>
            <SamplingDialog />
        </div>
    );
}