var Template = {

  board : "<a class='board waves-effect waves-light btn' href='../board.html?boardId={{id}}'>" +
                    "{{name}}" +
          "</a>",

  deck : "<div class='deck-wrapper'>" +
                "<div class='deck-content z-depth-1'>" +
                    "<div class='deck-header'>" +
                      "<div class='deck-header-name'>{{value}}</div>" +
                    "</div>" +
                    "<div class='deck-cards' id='deck-cards-{{id}}' data-deck-id='{{id}}'></div>" +
                    "<div class='card-composer'>" +
                       "<div class='add-card-form' id='add-card-form-{{id}}'>" +
                          "<textarea class='card-title' id='card-title-{{id}}'></textarea>" +
                          "<div class='btn-area'>" +
                            "<button class='btn waves-effect waves-light save-card' id='save-card-{{id}}'>save</button>" +
                            "<button class='btn waves-effect waves-light cancel-card' id='cancel-card-{{id}}'>cancel</button>" +
                          "</div>" +
                       "</div>" +
                       "<a class='add-card-btn' id='add-card-btn-{{id}}' href='#'>Add a Card...</a>" +
                    "</div>" +
                "</div>" +
              "</div>",

  card : "<div id='card-{{id}}' class='deck-card' draggable='true' data-card-id='{{id}}'>" +
  						"<div class='deck-card-detail'>" +
                  "<a class='deck-card-title modal-trigger modalLink' id='{{id}}' dir='auto' href='#'>{{value}}</a>" +
              "</div>" +
          "</div>",

  assignees : "<li class='assignees-list-item {{type}}' data-email='{{email}}'><span>{{name}}</span><i class='fa fa-check fa-lg' aria-hidden='true'></i></li>",

  comment :  "<div class='comment' id='comment-{{id}}'>" +
                "<div class='commenter'>{{writerName}}</div>" +
                "<div class='comment-contents z-depth-1'>{{commentContents}}</div>" +
                "<div class='comment-date'>{{currentTime}} - </div>" +
                "<div class='comment-delete' id='{{id}}'> delete</div>" +
    			  "</div>",
    member : "<li class='member'>" +
    "                <img src='./image/profile.png'/>" +
    "                <span class='member-name'>{{name}}</span>" +
    "            </li>"
};

export default Template;