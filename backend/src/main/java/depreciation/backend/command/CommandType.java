package depreciation.backend.command;


import depreciation.backend.command.admin.CreateContactCommand;
import depreciation.backend.command.admin.DeleteContactCommand;
import depreciation.backend.command.guest.LogInCommand;
import depreciation.backend.command.guest.RegisterCommand;
import depreciation.backend.command.user.*;
import depreciation.enums.RoleEnum;

import java.util.EnumSet;
import java.util.Set;


public enum CommandType {
    LOGIN {
        {
            this.command = new LogInCommand();
            this.role = EnumSet.of(RoleEnum.GUEST);
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
            this.role = EnumSet.of(RoleEnum.GUEST);
        }
    },
    LOGOUT {
        {
            this.command = new LogOutCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },


    GET_CONTACT {
        {
            this.command = new GetContactCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },
    GET_CONTACTS {
        {
            this.command = new GetContactsCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },
    UPDATE_CONTACT {
        {
            this.command = new UpdateContactCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },
    CREATE_CONTACT {
        {
            this.command = new CreateContactCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN);
        }
    },
    DELETE_CONTACT {
        {
            this.command = new DeleteContactCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN);
        }
    },


    CREATE_EQUIPMENT {
        {
            this.command = new CreateEquipmentCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },
    CREATE_COMPANY {
        {
            this.command = new CreateCompanyCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },
    GET_CONTACT_COMPANIES {
        {
            this.command = new GetContactCompaniesCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },
    GET_COMPANY_EQUIPMENT {
        {
            this.command = new GetCompanyEquipmentCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },

    GENERATE_REPORT {
        {
            this.command = new GenerateReportCommand();
            this.role = EnumSet.of(RoleEnum.ADMIN, RoleEnum.USER);
        }
    },


    EMPTY {
        {
            this.command = new EmptyCommand();
            this.role = EnumSet.of(RoleEnum.GUEST, RoleEnum.USER, RoleEnum.ADMIN);
        }
    };

    public ActionCommand command;
    public Set<RoleEnum> role;

    public ActionCommand getCommand() {
        return command;
    }
}
