import {_, boardUtils, API} from '../support/Utils.js';
import Template from '../support/template.js';

class CardModalHandler {
    constructor() {
        this.cardModal = _.$("#card-modal");
        this.cardModalEventHandler();
    }

    setDeckId(deckId) {
        this.deckId = deckId;
    }

    setCardId(cardId) {
        this.cardId = cardId;
    }

    getCardDetail(cardId, callback) {
        _.request(API.BOARDS.CARD(cardId), 'GET').then(callback);
    }

    saveComment(cardId, contents, callback) {
        const data = {
            "cardId" : cardId,
            "contents" : contents
        }
        _.request(API.BOARDS.COMMENTS(), 'POST', data).then(callback);
    }

    editDescription(cardId, description, callback) {
        const data = {
            "description": description
        };
        _.request(API.BOARDS.CARD_DESCRIPTION(cardId), 'PUT', data).then(callback);
    }

    deleteComment(commentId, callback) {
        _.request(API.BOARDS.COMMENT(commentId), 'DELETE').then(callback);
    }

    appendComment(res) {
        const status = res.status;

        if (status === 'OK') {
            this.printComment(res.content);
            _.$(".comment-contents").value = "";
        } else {
            console.log("appendComment error.")
        }
    }

    removeComment({status, content}) {

        if (status === 'OK') {
            const commentsDom = _.$(".comments");
            commentsDom.removeChild(document.getElementById(`comment-${content.id}`));
        } else {
            console.log("removeComment error.")
        }
    }

    printComments(comments) {
        this.resetComments();
        comments.forEach((comment) => {
            this.printComment(comment);
        });
    }

    printComment(comment) {
        const data = {
            "writerName" : comment.authorName,
            "commentContents" : comment.contents,
            "currentTime" : comment.createDate,
            "id" : comment.id
        };
        const template = boardUtils.createTemplate(Template.comment, data);
        _.$(".comments").insertAdjacentHTML('beforeend', template);
    }

    printDescription(res) {
        const card = res.content;

        _.$(".card-description").innerHTML = card.description;
        this.closeDescriptionField();
    }

    openCardModal(res) {
        const card = res.content;

        _.$(".card-title-in-modal").innerHTML = card.text;
        _.$(".deck-name").innerHTML = card.deckName;
        _.$(".card-description").innerHTML = card.description;
        _.$(".current-due-date").innerHTML = card.dueDate;

        this.setDueDateInputField(card.dueDate);

        this.printComments(card.comments);
        this.cardModal.classList.add("open");
        this.closeDescriptionField();
        this.closeDueDateModal();
        this.closeAssigneesModal();
    }

    closeCardModal() {
        this.cardModal.classList.remove("open");
    }

    openDescriptionField() {
        _.$(".card-description-textarea").value = _.$(".card-description").innerHTML;
        _.$(".card-description").classList.add("close");
        _.$(".card-description-edit").classList.add("open");
        _.$(".card-description-edit-btn").classList.add("close");
    }

    closeDescriptionField() {
        _.$(".card-description").classList.remove("close");
        _.$(".card-description-edit").classList.remove("open");
        _.$(".card-description-edit-btn").classList.remove("close");
        _.$(".comment-contents").value = "";
    }

    cardModalEventHandler() {
        _.eventHandler(".comments", "click", (e) => {
            e.preventDefault();
            const classList = e.target.classList;
            if (!classList.contains("comment-delete")) return;
            this.deleteComment(e.target.id, this.removeComment.bind(this));
        });

        _.eventHandler(".close-modal", "click", this.closeCardModal.bind(this));
        _.eventHandler(".card-description-edit-btn", "click", this.openDescriptionField.bind(this));
        _.eventHandler(".card-edit-close", "click", this.closeDescriptionField.bind(this));
        _.eventHandler(".card-edit-save", "click", () => {
            const description = _.$(".card-description-textarea").value;
            this.editDescription(this.cardId, description, this.printDescription.bind(this));
        });
        _.eventHandler(".comment-send", "click", () => {
            const contents = _.$(".comment-contents").value;
            this.saveComment(this.cardId, contents, this.appendComment.bind(this));
        });

        _.eventHandler(".due-date-btn", "click", this.toggleDueDateModal.bind(this));
        _.eventHandler(".due-date-send", "click", this.requestSaveDate.bind(this));
        _.eventHandler(".members-btn-in-card", "click", (e) => {
            this.toggleAssigneesModal();
            this.resetAssigneesList();
            _.request(API.BOARDS.CARD_ASSIGNEE(this.cardId), "GET").then((res) => {
                this.printMembers(res.content.assignees, "assignee");
                this.printMembers(res.content.boardMembers, "member");
            });
        });
        _.eventHandler(".assignees-list", "click", (e) => {
            const assigneeItem = e.target.closest("LI");
            if (!assigneeItem.classList.contains("assignee")) {
                this.requestAddAssignees(assigneeItem);
            } else {
                this.requestDeleteAssignees(assigneeItem);
            }
        })
    }

    printMembers(members, type) {
        _.$(".assignees-list").innerHTML += members.reduce((html, member) => {
            member["type"] = type;
            return html + boardUtils.createTemplate(Template.assignees, member);
        }, "");
    }

    resetComments() {
        _.$(".comments").innerHTML = "";
    }

    resetAssigneesList() {
        _.$(".assignees-list").innerHTML = "";
    }

    requestAddAssignees(assigneeItem) {
        const data = {
            "email": assigneeItem.dataset.email
        };
        _.request(API.BOARDS.CARD_ASSIGNEE(this.cardId), "POST", data)
            .then((res) => this.toggleAssigneeMember.call(this, res, assigneeItem));
    }

    requestDeleteAssignees(assigneeItem) {
        const data = {
            "email": assigneeItem.dataset.email
        };
        _.request(API.BOARDS.CARD_ASSIGNEE(this.cardId), "DELETE", data)
            .then((res) => this.toggleAssigneeMember.call(this, res, assigneeItem));
    }

    toggleAssigneeMember(res, assigneeItem) {
        if (res.status === "OK") {
            assigneeItem.classList.toggle("member");
            assigneeItem.classList.toggle("assignee");
        }
    }

    toggleAssigneesModal() {
        this.closeDueDateModal();
        _.$(".modal-for-members").classList.toggle("on")
    }

    closeAssigneesModal() {
        _.$(".modal-for-members").classList.remove("on")
    }

    setDueDateInputField(dueDate) {
        const dateInput = _.$("input[name=date]");
        const timeInput = _.$("input[name=time]");
        if (!dueDate) {
            dateInput.value = "";
            timeInput.value = "";
            return;
        }
        dateInput.value = dueDate.split(" ")[0];
        timeInput.value = dueDate.split(" ")[1];
    }

    toggleDueDateModal() {
        this.closeAssigneesModal();
        _.$(".modal-for-due-date").classList.toggle("on")
    }

    closeDueDateModal() {
        _.$(".modal-for-due-date").classList.remove("on");
    }

    requestSaveDate() {
        const date = _.$("input[name=date]").value;
        const time = _.$("input[name=time]").value;

        const data = {
            "dueDate": `${date}T${time}:00`
        };
        _.request(API.BOARDS.CARD_DATE(this.cardId), "PUT", data).then(this.printDueDate.bind(this));
    }

    printDueDate(res) {
        const card = res.content;

        _.$(".current-due-date").innerHTML = card.dueDate;
        this.toggleDueDateModal();
    }
}

export default CardModalHandler;