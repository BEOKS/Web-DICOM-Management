// action
const HEADER = 'ProjectDrawerReducer';
const TYPE = {
    SET_PROJECT: `${HEADER}/SET_PROJECT` as const,
    SET_CREATED_PROJECTS: `${HEADER}/SET_CREATED_PROJECTS` as const,
    SET_INVITED_PROJECTS: `${HEADER}/SET_INVITED_PROJECTS` as const,
    MARK_INVITED_PROJECT: `${HEADER}/MARK_INVITED_PROJECT` as const,
};

export const ProjectDrawerAction = {
    setProject: (project: Project) => ({ type: TYPE.SET_PROJECT, payload: project }),
    setCreatedProjects: (createdProjects: Project[]) => ({ type: TYPE.SET_CREATED_PROJECTS, payload: createdProjects }),
    setInvitedProjects: (invitedProjects: Project[]) => ({ type: TYPE.SET_INVITED_PROJECTS, payload: invitedProjects }),
    markInvitedProject: (marking: boolean) => ({ type: TYPE.MARK_INVITED_PROJECT, payload: marking }),
};

type ProjectDrawerActionType =
    ReturnType<typeof ProjectDrawerAction.setProject> |
    ReturnType<typeof ProjectDrawerAction.setCreatedProjects> |
    ReturnType<typeof ProjectDrawerAction.setInvitedProjects> |
    ReturnType<typeof ProjectDrawerAction.markInvitedProject>;


// state
export type User = {
    userId: string,
    name: string,
    email: string,
    picture: string,
    role: string
};

export type Project = {
    creator: User,
    projectId: string,
    projectName: string,
    visitor: User[]
};

type ProjectDrawerState = {
    project: Project,
    createdProjects: Project[],
    invitedProjects: Project[],
    isInvitedProject: boolean,
};

const INIT_PROJECT_DRAWER_STATE: ProjectDrawerState = {
    project: {
        creator: {
            userId: "",
            name: "",
            email: "",
            picture: "",
            role: ""
        },
        projectId: "",
        projectName: "현재 선택된 프로젝트가 없습니다.",
        visitor: [],
    },
    createdProjects: [],
    invitedProjects: [],
    isInvitedProject: false,
};


// reducer
export default function ProjectDrawerReducer(state: ProjectDrawerState = INIT_PROJECT_DRAWER_STATE, action: ProjectDrawerActionType): ProjectDrawerState {
    switch (action.type) {
        case TYPE.SET_PROJECT:
            return { ...state, project: action.payload };
        case TYPE.SET_CREATED_PROJECTS:
            return { ...state, createdProjects: action.payload };
        case TYPE.SET_INVITED_PROJECTS:
            return { ...state, invitedProjects: action.payload };
        case TYPE.MARK_INVITED_PROJECT:
            return { ...state, isInvitedProject: action.payload };
        default:
            return state;
    }
}