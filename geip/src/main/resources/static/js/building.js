
const columns = document.querySelectorAll(".column");
columns.forEach((column) => {
    new Sortable(column, {
        group: "shared",
        animation: 150,
        ghostClass: "blue-background-class"
    });

    column.addEventListener("drop", (e) => {
        e.preventDefault();
        console.log('username', e.currentTarget.querySelector(".username").textContent);
        console.log('rank', e.currentTarget.querySelector(".rank").textContent);
        console.log('target', e.currentTarget.closest('div').getAttribute("id"));
    });
});

var main={
    init : function (){
        var _this = this;

        $('#btn-save').on('click', function (){
            var group = {};
            var _team_array = [];
            var team = {};
            var warname = document.getElementById("matchname").value;
            group.matchName = warname;
            $('div.container div').each(function(index, item) {
                if(item.className == "column" && item.id.length > 0) {
                    console.log("item", item, item.id);
                    $("#"+item.id + ' span').each(function(index2, item2){
                        let cnt = index2 + 1;
                        if(item2.className == "username")
                            team.username = item2.textContent;
                        if(item2.className == "rank")
                            team.rank = item2.textContent;
                        if((cnt % 3) == 0)
                            _team_array.push({team : item.id, username : team.username, rank : team.rank});
                    });
                }
            });
            group.player = _team_array;
            _this.save(group);
        })
    },
    save : function (group){
        console.log("group  :",group);
        $.ajax({
            type: 'POST',
            url: '/api/teambuilding',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(group)
        }).done(function (){
            alert('저장되었습니다.')
        }).fail(function (error){
            alert(JSON.stringify(error));
        })

    },

    matchNameConfirm: function(competitionName) {
    		return new Promise((resolve, reject) => {
    			$.ajax({
    				url: "/api/record/matchName",
    				type: 'GET',
    				data: { competitionName },
    				success: function(res) {
    					resolve(res);
    				},
    				error: function(error) {
    					console.error("에러 : ", error);
    					reject(error);
    				}
    			});
    		})

    	}
}

main.init();
