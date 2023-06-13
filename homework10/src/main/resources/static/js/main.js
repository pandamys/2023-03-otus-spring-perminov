$(document).ready(function (){
    getAllBooks();
});

function getAllBooks() {
    let containerData = getContainerData();
    let containerAction = getContainerAction();
    clearContainer(true, true);
    containerData.append(createTable());
    containerAction.append($("<a>").addClass("add-item").attr({href: "#"}).text("Add book"));
    fetch("/api/book")
        .then(response => response.json())
        .then(json => fillTable("table-books", json));
}

function fillTable(nameTable, data){
    let table = $("." + nameTable);
    if (table.length){
        data.forEach(book => {
            tr = $("<tr/>");
            tr.append($("<td/>").addClass("id-item").append(book.id))
              .append($("<td/>").append($('<a href="#">').addClass("view-item").text(book.name)))
              .append($("<td/>").append(book.author.surname + " " + book.author.name))
              .append($("<td/>").append(book.genre.name))
              .append($("<td/>").append($('<a href="#">').addClass("edit-item").text("edit")))
              .append($("<td/>").append($('<a href="#">').addClass("delete-item").text("delete")));
            table.append(tr);
        });
        addCreateAction();
        addViewAction();
        addEditAction();
        addDeleteAction();
    }
}

function addViewAction(){
    let viewElement = $(".view-item");
    viewElement.on("click", function(e){
        element = $(e.target);
        id = Number(element.parents("tr").find(".id-item").text());
        fetch("/api/book/" + id)
            .then(response => response.json())
            .then(json => {infoAboutBook(json)});
    });
}

function addCreateAction(){
    let createElement = $(".add-item");
    createElement.on("click", function(e){
        element = $(e.target);
        formBook("add");
    });
}

function addEditAction(){
    let editElement = $(".edit-item");
    editElement.on("click", function(e){
        element = $(e.target);
        id = Number(element.parents("tr").find(".id-item").text());
        fetch("/api/book/" + id)
            .then(response => response.json())
            .then(json => {formBook("edit", json)});
    });
}

function addDeleteAction(){
    let delElement = $(".delete-item");
    delElement.on("click", function(e){
        element = $(e.target);
        id = Number(element.parents("tr").find(".id-item").text());
        fetch("/api/book/" + id, {method: "delete"}).then(() => window.location.reload());
    });
}

function infoAboutBook(data){
    let containerData = getContainerData();
    let containerAction = getContainerAction();
    if (containerData.length){
        containerData.empty();
        list = $("<ul>");
        list.append($("<li>").append("Name: " + data.name))
            .append($("<li>").append("Author: " + data.author.name + " " + data.author.surname))
            .append($("<li>").append("Genre: " + data.genre.name))
        if (data.comments.length){
            listComments = $("<ul>").append("Comments");
            data.comments.forEach(comment => {
                listComments.append($("<li>").append(comment.text));
            });
            list.append(listComments);
        } else {
            list.append("Comments not found");
        }
        containerData.append(list);
    }

    if (containerAction.length){
        containerAction.empty();
        linkBack = $('<a href="#">').text("Back");
        linkBack.on("click", function(){
            getAllBooks();
        });
        containerAction.append(linkBack);
    }
}

function createTable(){
    let tableBooks = $("<table>").addClass("table-books");
    tableBooks.append($("<thead>")
        .append($("<tr>")
            .append($("<th>").text("ID"))
            .append($("<th>").text("Name"))
            .append($("<th>").text("Author"))
            .append($("<th>").text("Genre"))
            .append($("<th colspan=\"2\">").text("Action"))));
    tableBooks.append($("<tbody>"));
    return tableBooks;
}

function formBook(mode, data){
    let containerData = getContainerData();
    let containerAction = getContainerAction();
    let fields = [
        {id: "id", name: "ID", type: "input"},
        {id: "name", name: "Name", type: "input"},
        {id: "author", name: "Author", type: "select"},
        {id: "genre", name: "Genre", type: "select"}
    ];
    let formBook = $("<form>").addClass("add-new-book");
    clearContainer(true, true);
    for (field of fields){
        label = $("<label>").attr({for: field.id}).text(field.name);
        if (field.type == "select"){
            element = $("<select>");
        } else if (field.type == "input"){
            element = $("<input>");
        }
        element.attr({id: field.id + "-input", name: field.id});
        formBook.append($("<div>").addClass("field-group").append(label).append(element));
    }

    containerData.append(formBook);

    cancelButton = $("<a>").attr({href: "#"}).append($("<button>").attr({type: "button"}).text("Cancel"));

    containerAction.append($("<button>").attr({id: "send-form", type: "submit"}).text("Save"));
    containerAction.append(cancelButton);
    cancelButton.on("click", function(){
        getAllBooks();
    });

    if (mode == "edit"){
        formBook.attr({id: "edit-book"})
        formBook.find("#id-input").attr({readonly: "readonly"});
    } else if (mode == "add"){
        formBook.attr({id: "add-book"})
        formBook.find("#id-input").parent().remove();
    }
    loadPage(data, mode);
}

function loadPage(data, mode) {
    let form = $("form");
    if (form.length){
        console.log(data);
        if (data != undefined){
            $("#id-input").val(data.id);
            $("#name-input").val(data.name)
        }
        fetch("/api/author")
            .then(response => response.json())
            .then(json => fillAuthors(form.find("#author-input"), json));
        fetch("/api/genre")
            .then(response => response.json())
            .then(json => fillGenre(form.find("#genre-input"), json));
        addActionButton(mode);
    }
}

function fillAuthors(element, data){
    data.forEach(author => {
        element.append($("<option>").text(author.fullName).attr({"value": author.id}));
    });
}

function fillGenre(element, data){
    data.forEach(genre => {
        element.append($("<option>").text(genre.name).attr({"value": genre.id}));
    });
}

function clearContainer(clearData, clearAction){
    let containerData = getContainerData();
    let containerAction = getContainerAction();
    if (containerData.length && clearData){
        containerData.empty();
    }
    if (containerAction.length && clearAction){
        containerAction.empty();
    }
}

function getContainerData(){
    return $(".container-data");
}

function getContainerAction(){
    return $(".container-action");
}

function addActionButton(mode){
    let actionButton = $(".container-action #send-form");
    let form = $("form");
    if (actionButton.length && form.length){
        actionButton.on("click", function(){
            id = form.find("#id-input").val();
            name = form.find("#name-input").val();
            authorId = form.find("#author-input").find("option:selected").val();
            genreId = form.find("#genre-input").find("option:selected").val();
            console.log(name + " " + authorId + " " + genreId);
            formData = {
                id: id,
                name: name,
                authorId: authorId,
                genreId: genreId
            }

            url = "/api/book/"
            if (mode == "edit"){
                url = url + id;
                method = "PUT";
            } else {
                method = "POST";
            }

            fetch(url, {
                method: method,
                body: JSON.stringify(formData),
                headers: {
                      "Content-Type": "application/json",
                }
            }).then(response => console.log(response.text()))
              .then(response => {window.location.href = "/book"})
        });
    }
}