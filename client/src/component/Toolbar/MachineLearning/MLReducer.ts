// action
const HEADER = 'MLReducer';
const TYPE = {
    OPEN_DIALOG: `${HEADER}/OPEN_DIALOG` as const,
    CLOSE_DIALOG: `${HEADER}/CLOSE_DIALOG` as const,
    OPEN_SNACKBAR: `${HEADER}/OPEN_SNACKBAR` as const,
    UPDATE_SNACKBAR: `${HEADER}/UPDATE_SNACKBAR` as const,
    CLOSE_SNACKBAR: `${HEADER}/CLOSE_SNACKBAR` as const
};

export const MLAction = {
    openDialog: () => ({ type: TYPE.OPEN_DIALOG }),
    closeDialog: () => ({ type: TYPE.CLOSE_DIALOG }),
    openSnackbar: () => ({ type: TYPE.OPEN_SNACKBAR }),
    updateSnackbar: () => ({ type: TYPE.UPDATE_SNACKBAR }),
    closeSnackbar: () => ({ type: TYPE.CLOSE_SNACKBAR })
};

type MLActionType =
    ReturnType<typeof MLAction.openDialog> |
    ReturnType<typeof MLAction.closeDialog> |
    ReturnType<typeof MLAction.openSnackbar> |
    ReturnType<typeof MLAction.updateSnackbar> |
    ReturnType<typeof MLAction.closeSnackbar>;

// state
type snackbarInfoType = {
    open: boolean,
    message: string,
    progress: number
};

type MLStateType = {
    dialogOpen: boolean,
    snackbarInfo: snackbarInfoType
};

const INIT_SAMPLING_STATE: MLStateType = {
    dialogOpen: false,
    snackbarInfo: {
        open: false,
        message: 'Machine Learning...',
        progress: 0
    }
};

// reducer
export default function SamplingReducer(state: MLStateType = INIT_SAMPLING_STATE, action: MLActionType): MLStateType {
    switch (action.type) {
        case TYPE.OPEN_DIALOG:
            return { ...state, dialogOpen: true, snackbarInfo: { ...state.snackbarInfo, message: 'Machine Learning...', progress: 0 } };
        case TYPE.CLOSE_DIALOG:
            return { ...state, dialogOpen: false };
        case TYPE.OPEN_SNACKBAR:
            return { ...state, snackbarInfo: { ...state.snackbarInfo, open: true } };
        case TYPE.UPDATE_SNACKBAR:
            return { ...state, snackbarInfo: { ...state.snackbarInfo, message: 'Machine Learning Finished!', progress: 100 } };
        case TYPE.CLOSE_SNACKBAR:
            return { ...state, snackbarInfo: { ...state.snackbarInfo, open: false } };
        default:
            return state;
    }
}