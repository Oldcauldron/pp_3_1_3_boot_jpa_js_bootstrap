let modal = $('#defaultModal');
let modalTitle = $('.modal-title');
let modalBody = $('.modal-body');
let modalFooter = $('.modal-footer');

let clearFormButton = $('<button type="reset" class="btn btn-secondary">Clear</button>');
let primaryButton = $('<button type="button" class="btn btn-primary"></button>');
let dismissButton = $('<button type="button" class="btn btn-secondary" data-dismiss="modal"></button>');
let dangerButton = $('<button type="button" class="btn btn-danger"></button>');

// $(document).ready(function(){
$(function(){
    addNewUserTab();
    adminTabAllUsers();
    defaultModal();
    profileAuthUserTable()

    fillNavkaPanel();
});



async function adminTabAllUsers() {
    $('#usersTable tbody').empty();
    const userResponse = await userService.findAll();
    const usersJson = userResponse.json();
    usersJson.then(users => {
        users.forEach(user => {
            let userRoles2 = ``
            for(let role of user.roles) {
                userRoles2 += role.role + ', '
            }

            let userRow = `$(<tr>
                        <th scope="row">${user.id}</th>
                        <td>${user.email}</td>
                        <td>${user.name}</td>
                        <td>${user.age}</td>
                        <td>${userRoles2.slice(0, -2)}</td>
                        
                        <td class="text-center">
                            <div class="btn-group" role="group" aria-label="Action Buttons">
                            <button type="button" class="btn btn-info" data-toggle="modal" id="editButton"
                            data-id="${user.id}" data-action="editUser" data-toggle="modal" data-target="#defaultModal">
                                        Edit
                            </button>
                            </div>
                        </td>
                        
                        <td class="text-center">
                            <div class="btn-group" role="group" aria-label="Action Buttons">
                            <button type="button" class="btn btn-danger btn-primary" data-toggle="modal" id="deleteButton"
                            data-id="${user.id}" data-action="deleteUser" data-toggle="modal" data-target="#defaultModal">
                                            Delete
                            </button>
                            </div>
                        </td> 
                         
                    </tr>)`;

            $('#usersTable tbody').append(userRow);
        });
    });
}




async function addNewUserTab() {

    let roleResponse = await roleService.findAll();
    let roleJson = roleResponse.json();

    let addNewUserForm = $('#addNewUserForm');
    roleJson.then(roles => {
        roles.forEach(role => {
            addNewUserForm.find('#newRoles').append(new Option(role.role, role.id));
        })
    })
    for(let i = 0; i < 150; i++) {
        addNewUserForm.find('#newAge').append(new Option(i, i));
    }

     $('#addNewUserButton').on('click', async e => {

        let email =  addNewUserForm.find('#newEmail').val().trim();
        let name =  addNewUserForm.find('#newName').val().trim();
        let password =  addNewUserForm.find('#newPassword').val().trim();
        let age = addNewUserForm.find('#newAge option:selected').val().trim();
        let rolesIdInArr = addNewUserForm.find('#newRoles').val();

        let finalRolesArr = [];
        await roleJson.then(roles => {
            roles.forEach(role => {
                rolesIdInArr.forEach(roleId => {
                    if (role.id == roleId) {
                        finalRolesArr.push(role);
                    }
                });
            });
        });

        let data = {
            email: email,
            name: name,
            password: password,
            age: age,
            roles: finalRolesArr
        }

        const addResponse = await userService.add(data);
        let addNewUserPanel = $('#addNewUserPanel');

        if (addResponse.ok) {
            adminTabAllUsers();
            addNewUserPanel.find('#messageBodyErrOrSucc').remove();
            let succ = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert" id="messageBodyErrOrSucc">
                                New user ${name} was added!
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>`;
            addNewUserPanel.prepend(succ);

            addNewUserForm.find('#newEmail').val('');
            addNewUserForm.find('#newName').val('');
            addNewUserForm.find('#newPassword').val('');
            addNewUserForm.find('#newAge option:selected').val('');
            addNewUserForm.find('#newRoles').val('');

        } else {
            let body = await addResponse.json();
            addNewUserPanel.find('#messageBodyErrOrSucc').remove();
                let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageBodyErrOrSucc">
                                ${body.info}
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>`;
            addNewUserPanel.prepend(alert);
        }
    })

}









function defaultModal() {
    $('#defaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false,
    }).on("show.bs.modal", function(event){
        let button = $(event.relatedTarget);
        let id = button.data('id');
        let action = button.data('action');
        switch(action) {
            case 'viewBook':
                viewBook($(this), id);
                break;

            case 'addBook':
                addBook($(this));
                break;

            case 'editUser':
                editUser($(this), id);
                break;

            case 'deleteUser':
                deleteUser($(this), id);
                break;

            case 'viewCategory':
                viewCategory($(this), id);
                break;

            case 'addCategory':
                addCategory($(this));
                break;

            case 'editCategory':
                editCategory($(this), id);
                break;
        }
    }).on('hidden.bs.modal', function(event){
        $(this).find('.modal-title').html('');
        $(this).find('.modal-body').html('');
        $(this).find('.modal-footer').html('');
    });
}









async function editUser(modal, id) {
        const userResponse = await userService.findById(id);
        const userJson = userResponse.json();

        const rolesResponse = await roleService.findAll();
        const rolesJson = rolesResponse.json();

        let idInput = `<div class="form-group">
                <label for="id">ID</label>
                <input type="text" class="form-control" id="id" name="id" disabled>
                <div class="invalid-feedback"></div>
            </div>`;

        modal.find(modalTitle).html('Edit User');

        let userFormHidden = $('.userForm:hidden')[0];
        modal.find(modalBody).html($(userFormHidden).clone());
        let userForm = modal.find('.userForm');

        userForm.prop('id', 'updateUserForm');
        modal.find(userForm).prepend(idInput);
        modal.find(userForm).show();

        dismissButton.html('Cancel');
        modal.find(modalFooter).append(dismissButton);

        primaryButton.prop('id', 'updateUserButton');
        primaryButton.html('Update');
        modal.find(modalFooter).append(primaryButton);



        // заполняем форму данными юзера
        userJson.then(user => {
            modal.find('#id').val(user.id).prop('disabled', true);
            modal.find('#email').val(user.email);
            modal.find('#name').val(user.name);
            modal.find('#password').val('');
            for(let i = 0; i < 150; i++) {
                if (user.age == i) {
                    modal.find('#age').append(new Option(i, i, false, true));
                } else {
                    modal.find('#age').append(new Option(i, i));
                }
            }

            rolesJson.then(roles => {
                roles.forEach(role => {
                    user.roles.forEach(userRole => {
                        if (userRole.id == role.id) {
                            modal.find('#roles').append(new Option(role.role, role.id, false, true));
                            role = true;
                        }
                    });
                    (role !== true) ? modal.find('#roles').append(new Option(role.role, role.id)) : null;
                });
            });
        });


        $('#updateUserButton').click(async function(e){
            let id = userForm.find('#id').val().trim();
            let email = userForm.find('#email').val().trim();
            let name = userForm.find('#name').val().trim();
            let password = userForm.find('#password').val().trim();
            let age = userForm.find('#age option:selected').val().trim();
            let rolesIdArr = userForm.find('#roles').val();

            // ROLES
            let userRoles = [];
            await rolesJson.then(roles => {
                roles.forEach(role => {
                    rolesIdArr.forEach(roleId => roleId == role.id ? userRoles.push(role) : null)
                });
            });

            let data = {
                id: id,
                email: email,
                name: name,
                password: password,
                age: age,
                roles: userRoles
            };

            const userResponse = await userService.update(id, data);

            if (userResponse.status == 200) {
                adminTabAllUsers();
                modal.find('.modal-title').html('Success');
                modal.find('.modal-body').html('User updated!');

                dismissButton.html('Close');
                modal.find(modalFooter).html(dismissButton);

                $('#defaultModal').modal('show');
            } else {
                let maybeBody = await userResponse.json();
                $('#sharaBaraMessageError').remove();
                let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                                ${maybeBody.info}
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>`;
                    modal.find('.modal-body').prepend(alert);
            }
        });
}













async function deleteUser(modal, id) {
    const userResponse = await userService.findById(id);
    const userJson = userResponse.json();

    modal.find(modalTitle).html('Delete user');

    let message = '<strong>Are you sure to delete the following user?</strong>';
    modal.find(modalBody).html(message);

    let viewUserTableHidden = $('.userForm:hidden')[0];
    modal.find(modalBody).append($(viewUserTableHidden).clone());
    let viewUserTable = modal.find('.userForm');
    modal.find(viewUserTable).show();

    dismissButton.html('Close');
    modal.find(modalFooter).append(dismissButton);

    dangerButton.prop('id', 'deleteUserButton');
    dangerButton.html('Delete');
    modal.find(modalFooter).append(dangerButton);

    let idInput = `<div class="form-group">
                <label for="id">ID</label>
                <input type="text" class="form-control" id="id" name="id" disabled>
                <div class="invalid-feedback"></div>
            </div>`;
    modal.find(viewUserTable).prepend(idInput);

    // заполняем форму данными юзера
    userJson.then(user => {
        modal.find('#id').val(user.id);
        modal.find('#email').val(user.email).prop('disabled', true);
        modal.find('#name').val(user.name).prop('disabled', true);
        modal.find('#password').parent().remove();

        modal.find('#age').append(new Option(user.age, user.age, false, true));
        modal.find('#age').prop('disabled', true);

        user.roles.forEach(userRole => {
            modal.find('#roles').append(new Option(userRole.role, userRole.id, false, true));
        })
        modal.find('#roles').prop('disabled', true);
    })


    $('#deleteUserButton').click(async function(e){
        e.preventDefault();
        const userResponse = await userService.delete(id);

        if (userResponse.ok) {
            adminTabAllUsers();
            modal.find('.modal-title').html('Success');
            modal.find('.modal-body').html('User deleted!');
            dismissButton.html('Close');
            modal.find(modalFooter).html(dismissButton);
            $('#defaultModal').modal('show');
        } else {
            userResponse.json().then(response => {
                let alert = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert">
                            ${response.error}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
                modal.find('.modal-body').prepend(alert);
            });
        }
    });
}









async function profileAuthUserTable() {
    let table = $('#profileAuthUserTable tbody');

    const authUserResponse = await userService.findAuthUser();
    const authUserJson = authUserResponse.json();

    table.empty();
    let data = '';
    await authUserJson.then(async user => {
        let rolesString = ''
        await user.roles.forEach(role => rolesString += `${role.role}, `);
        data += `<tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${rolesString.slice(0, -2)}</td>
                </tr>`
    })
    table.append(data);
}

async function showProfileTable() {
    $('#v-pills-profile-tab').on('click', async e => {
        await profileAuthUserTable();
    })

}



async function fillNavkaPanel() {
    let navka = $('#headNavkaPanel');
    navka.empty();
    const authUserResponse = await userService.findAuthUser();
    const authUserJson = authUserResponse.json();

    let data = '';
    await authUserJson.then(async user => {
        let rolesString = ''
        await user.roles.forEach(role => rolesString += `${role.role}, `);
        data += `<span>${user.email}, with roles: ${rolesString.slice(0, -2)}</span>`
    })
    navka.append(data);
}














const http = {
    fetch: async function(url, options = {}) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });

        return response;
    }
};

const userService = {
    findAll: async () => {
        return await http.fetch('/api/users');
    },
    add: async (data) => {
        return await http.fetch('/api/users', {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },
    findById: async (id) => {
        return await http.fetch('/api/users/' + id);
    },
    update: async (id, data) => {
        return await http.fetch('/api/users/' + id, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    },
    delete: async (id) => {
        return await http.fetch('/api/users/' + id, {
            method: 'DELETE'
        });
    },
    findAuthUser: async () => {
        return await http.fetch('/api/user', {
            method: 'GET'
        });
    },
};





const roleService = {
    findAll: async () => {
        return await http.fetch('/api/roles');
    }
};







// const requestUrl = '/api/users'
// const body = {
//     email: "osmsdfunus@a.com",
//     password: "$2a$10$nObhgcAc3pLmzUs5yzds4.OT6/w2Mi0kaM3tie4fMZg/iIxrEyUSC",
//     name: "omenu3s",
//     age: 123,
//     roles: [
//         {
//             id: 2,
//             role: "ROLE_USER",
//             authority: "ROLE_USER"
//         }
//     ]
// }
//
//
// function sendRequest(method, url, body=null) {
//     const headers = {
//         'Content-Type': 'application/json'
//     }
//
//     return fetch(url, {
//         method: method,
//         headers: headers,
//         body: body ? (JSON.stringify(body)) : null
//     })
//         .then(response => {
//             if(response.ok) {
//                 return response.json()
//             }
//             return response.json()
//                 .then(err => {
//                     const e = new Error('Что то пошло не так')
//                     e.data = err
//                     throw e;
//                 })
//         })
// }

