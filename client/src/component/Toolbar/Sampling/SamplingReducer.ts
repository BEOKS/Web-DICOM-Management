// action
const HEADER = 'SamplingReducer';
const TYPE = {
    OPEN_DIALOG: `${HEADER}/OPEN_DIALOG` as const,
    CLOSE_DIALOG: `${HEADER}/CLOSE_DIALOG` as const,
    OPEN_SNACKBAR: `${HEADER}/OPEN_SNACKBAR` as const,
    UPDATE_SNACKBAR: `${HEADER}/UPDATE_SNACKBAR` as const,
    CLOSE_SNACKBAR: `${HEADER}/CLOSE_SNACKBAR` as const
};

export const SamplingAction = {
    openDialog: () => ({ type: TYPE.OPEN_DIALOG }),
    closeDialog: () => ({ type: TYPE.CLOSE_DIALOG }),
    openSnackbar: () => ({ type: TYPE.OPEN_SNACKBAR }),
    updateSnackbar: () => ({ type: TYPE.UPDATE_SNACKBAR }),
    closeSnackbar: () => ({ type: TYPE.CLOSE_SNACKBAR })
};

type SamplingActionType =
    ReturnType<typeof SamplingAction.openDialog> |
    ReturnType<typeof SamplingAction.closeDialog> |
    ReturnType<typeof SamplingAction.openSnackbar> |
    ReturnType<typeof SamplingAction.updateSnackbar> |
    ReturnType<typeof SamplingAction.closeSnackbar>;

// state
type snackbarInfoType = {
    open: boolean,
    message: string,
    progress: number
};

type SamplingStateType = {
    dialogOpen: boolean,
    snackbarInfo: snackbarInfoType
};

const INIT_SAMPLING_STATE: SamplingStateType = {
    dialogOpen: false,
    snackbarInfo: {
        open: false,
        message: 'Sampling...',
        progress: 0
    }
};

// reducer
export default function SamplingReducer(state: SamplingStateType = INIT_SAMPLING_STATE, action: SamplingActionType): SamplingStateType {
    switch (action.type) {
        case TYPE.OPEN_DIALOG:
            return { ...state, dialogOpen: true, snackbarInfo: { ...state.snackbarInfo, message: 'Sampling...', progress: 0 } };
        case TYPE.CLOSE_DIALOG:
            return { ...state, dialogOpen: false };
        case TYPE.OPEN_SNACKBAR:
            return { ...state, snackbarInfo: { ...state.snackbarInfo, open: true } };
        case TYPE.UPDATE_SNACKBAR:
            return { ...state, snackbarInfo: { ...state.snackbarInfo, message: 'Sampling Finished!', progress: 100 } };
        case TYPE.CLOSE_SNACKBAR:
            return { ...state, snackbarInfo: { ...state.snackbarInfo, open: false } };
        default:
            return state;
    }
}