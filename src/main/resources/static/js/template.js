var Template = {

  board : "<li class='board waves-effect waves-light btn'>" +
                    "{{input-value}}" +
          "</li>",

  deck : "<div class='deck-wrapper'>" +
                "<div class='deck-content z-depth-1'>" +
                    "<div class='deck-header'>" +
                      "<textarea class='deck-header-name'>{{value}}</textarea>" +
                    "</div>" +
                    "<div class='deck-cards'></div>" +
                    "<div class='card-composer'>" +
                       "<div class='add-card-form'>" +
                          "<textarea class='card-title'></textarea>" +
                          "<div class='btn-area'>" +
                            "<button class='btn waves-effect waves-light save-card'>save</button>" +
                            "<button class='btn waves-effect waves-light cancel-card'>cancel</button>" +
                          "</div>" +
                       "</div>" +
                       "<a class='add-card-btn' href='#'>Add a Card...</a>" +
                    "</div>" +
                "</div>" +
              "</div>",
  card : "<div class='deck-card'>" +
  						"<div class='deck-card-detail'>" +
                  "<a class='deck-card-title modal-trigger modalLink' dir='auto' href='#'>{{value}}</a>" +
              "</div>" +
          "</div>",

  comment :  "<div class='comment'>" +
                "<div class='commenter'>{writer-name}</div>" +
                "<div class='comment-contents z-depth-1'>{{comment-contents}}</div>" +
                "<div class='comment-date'>{{current-time}} - </div>" +
                "<div class='comment-reply'> Reply</div>" +
    			  "</div>"

};
