// action
const HEADER = 'VisualTableReducer';
const TYPE = {
    SET_OPTIONS: `${HEADER}/SET_OPTIONS` as const
};

export const VisualTableAction = {
    setOptions: (options: OptionsType) => ({ type: TYPE.SET_OPTIONS, payload: options })
};

type VisualTableActionType =
    ReturnType<typeof VisualTableAction.setOptions>


// state
type OptionsType = string[];

type VisualTableStateType = {
    options: OptionsType
}

const INIT_VISUALTABLE_STATE: VisualTableStateType = {
    options: []
};


// reducer
export default function SamplingReducer(state: VisualTableStateType = INIT_VISUALTABLE_STATE, action: VisualTableActionType): VisualTableStateType {
    switch (action.type) {
        case TYPE.SET_OPTIONS:
            return { ...state, options: action.payload };
        default:
            return state;
    }
}