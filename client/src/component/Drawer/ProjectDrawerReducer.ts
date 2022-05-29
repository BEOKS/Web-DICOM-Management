// action
const HEADER = 'ProjectDrawerReducer';
const TYPE = {
    SET_PROJECT: `${HEADER}/SET_PROJECT` as const,
    SET_CREATED_PROJECTS: `${HEADER}/SET_CREATED_PROJECTS` as const,
    SET_INVITED_PROJECTS: `${HEADER}/SET_INVITED_PROJECTS` as const,
    MARK_INVITED_PROJECT: `${HEADER}/MARK_INVITED_PROJECT` as const,
    OPEN_PROJECT_DRAWER: `${HEADER}/OPEN_PROJECT_DRAWER` as const,
    CLOSE_PROJECT_DRAWER: `${HEADER}/CLOSE_PROJECT_DRAWER` as const,
    OPEN_CREATE_PROJECT_DIALOG: `${HEADER}/OPEN_CREATE_PROJECT_DIALOG` as const,
    CLOSE_CREATE_PROJECT_DIALOG: `${HEADER}/CLOSE_CREATE_PROJECT_DIALOG` as const,
    SET_NEW_PROJECT_NAME: `${HEADER}/SET_NEW_PROJECT_NAME` as const,
};

export const ProjectDrawerAction = {
    setProject: (project: Project) => ({ type: TYPE.SET_PROJECT, payload: project }),
    setCreatedProjects: (createdProjects: Project[]) => ({ type: TYPE.SET_CREATED_PROJECTS, payload: createdProjects }),
    setInvitedProjects: (invitedProjects: Project[]) => ({ type: TYPE.SET_INVITED_PROJECTS, payload: invitedProjects }),
    markInvitedProject: (marking: boolean) => ({ type: TYPE.MARK_INVITED_PROJECT, payload: marking }),
    openProjectDrawer: () => ({ type: TYPE.OPEN_PROJECT_DRAWER }),
    closeProjectDrawer: () => ({ type: TYPE.CLOSE_PROJECT_DRAWER }),
    openCreateProjectDialog: () => ({ type: TYPE.OPEN_CREATE_PROJECT_DIALOG }),
    closeCreateProjectDialog: () => ({ type: TYPE.CLOSE_CREATE_PROJECT_DIALOG }),
    setNewProjectName: (projectName: string) => ({ type: TYPE.SET_NEW_PROJECT_NAME, payload: projectName }),
};

type ProjectDrawerActionType =
    ReturnType<typeof ProjectDrawerAction.setProject> |
    ReturnType<typeof ProjectDrawerAction.setCreatedProjects> |
    ReturnType<typeof ProjectDrawerAction.setInvitedProjects> |
    ReturnType<typeof ProjectDrawerAction.markInvitedProject> |
    ReturnType<typeof ProjectDrawerAction.openProjectDrawer> |
    ReturnType<typeof ProjectDrawerAction.closeProjectDrawer> |
    ReturnType<typeof ProjectDrawerAction.openCreateProjectDialog> |
    ReturnType<typeof ProjectDrawerAction.closeCreateProjectDialog> |
    ReturnType<typeof ProjectDrawerAction.setNewProjectName>;


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
    projectName: string | undefined,
    visitor: User[]
};

type ProjectDrawerState = {
    project: Project,
    createdProjects: Project[],
    invitedProjects: Project[],
    isInvitedProject: boolean,
    openProjectDrawer: boolean,
    openCreateProjectDialog: boolean,
    newProjectName: string
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
        projectName: undefined,
        visitor: [],
    },
    createdProjects: [],
    invitedProjects: [],
    isInvitedProject: false,
    openProjectDrawer: false,
    openCreateProjectDialog: false,
    newProjectName: "",
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
        case TYPE.OPEN_PROJECT_DRAWER:
            return { ...state, openProjectDrawer: true };
        case TYPE.CLOSE_PROJECT_DRAWER:
            return { ...state, openProjectDrawer: false };
        case TYPE.OPEN_CREATE_PROJECT_DIALOG:
            return { ...state, openCreateProjectDialog: true };
        case TYPE.CLOSE_CREATE_PROJECT_DIALOG:
            return { ...state, openCreateProjectDialog: false };
        case TYPE.SET_NEW_PROJECT_NAME:
            return { ...state, newProjectName: action.payload };
        default:
            return state;
    }
}