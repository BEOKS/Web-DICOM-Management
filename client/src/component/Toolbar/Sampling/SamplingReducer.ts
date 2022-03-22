// action
const HEADER = 'SamplingReducer';
const TYPE = {
    OPEN_DIALOG: `${HEADER}/OPEN_DIALOG` as const,
    CLOSE_DIALOG: `${HEADER}/CLOSE_DIALOG` as const
};

export const SamplingAction = {
    openDialog: () => ({ type: TYPE.OPEN_DIALOG }),
    closeDialog: () => ({ type: TYPE.CLOSE_DIALOG })
};

type SamplingActionType =
    ReturnType<typeof SamplingAction.openDialog> |
    ReturnType<typeof SamplingAction.closeDialog>;

// state
type SamplingStateType = {
    dialogStatus: boolean
};

const INIT_SAMPLING_STATE: SamplingStateType = {
    dialogStatus: false
};

// reducer
export default function SamplingReducer(state: SamplingStateType = INIT_SAMPLING_STATE, action: SamplingActionType) {
    switch (action.type) {
        case TYPE.OPEN_DIALOG:
            return { ...state, dialogStatus: true };
        case TYPE.CLOSE_DIALOG:
            return { ...state, dialogStatus: false };
        default:
            return state;
    }
}