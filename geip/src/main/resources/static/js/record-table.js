const recodeTable={

	init:()=>{

		$("#searchBtn").on("click",()=>{
			recodeTable.searchBtn();
		});

	},

	searchBtn: function(){
		const keyword=document.querySelector("#keyword");
				if(!keyword.value){
					alert("유저이름을 입력해 주세요.");
					keyword.focus();
					return;
		}

		$.get(`/api/record/table/${keyword.value}`, function(data,status){
				const redTeam=data.redTeamList;
				const blueTeam=data.blueTeamList;

				const competitionNameArry=redTeam.map((item)=>{
					return item.competition_name;
				});

				//중복제거
				const competitionName=[...new Set(competitionNameArry)];


			    const redGroupIdArry=redTeam.map((item)=>{
					return item.groupId;
				});

				const redGroupId=[...new Set(redGroupIdArry)];

			    const blueGroupIdArry=redTeam.map((item)=>{
					return item.groupId;
				});
				const blueGroupId=[...new Set(blueGroupIdArry)];



				if(competitionName.length===0){
					$(".war-recode-container h3").html("검색된 데이터가 없습니다.");
					$("#war-record").html("");
					return;
				}
				const competHead=`<div class="column" >
										<h1 class="red">TEAM RED</h1>
									</div>
									<div class="column" >
										<h1 class="name">매치 이름</h1>

									</div>
									<div class="column" >
										<h1 class="blue" >TEAM BLUE</h1>
						</div>`	;
				$(".war-recode-container h3").html(competHead);



				let html='';

				competitionName.forEach((competitionName, index)=>{

					html +=`<div class="info" >`;

                    let redMatchStyle="";
					redTeam.forEach(item=>{
						if(competitionName===item.competition_name){
							 if(keyword.value===item.lol_nick){
								if(item.win_lose!==null){
									redMatchStyle= `${item.win_lose==='WIN' ? 'blueActive' :'redActive'}`;
								}
							 }
						}
					})


					let redCnt=0;
					redTeam.forEach((item)=>{
						  if(redCnt===0 && competitionName===item.competition_name ){
							 html +=`<div class="left_area ${redMatchStyle}"

							 data-groupid="${item.groupId}" data-index="${index}" id="red-${index}"

							 data-team="RED" >${item.win_lose!==null?item.win_lose:''}</div>`;
							 ++redCnt;
						  }
					})


				 	html +=`<div><ul>`;

					redTeam.forEach(item=>{
						  if(competitionName===item.competition_name){

							if(keyword.value===item.lol_nick){
								html +=`<li><div class="nick">${item.lol_nick}</div></li>`;
							 }else{
								html +=`<li><div>${item.lol_nick}</div></li>`;
							 }

						}

					})

                    html +=`</ul></div>
                                <div class="competition">
                                    <div>${competitionName}</div>
                                </div>
                                <div class="blueTeam">
                                <ul>`;

                    let blueMatchStyle="";
					blueTeam.forEach(item=>{
						if(competitionName===item.competition_name){

							 if(keyword.value===item.lol_nick){
								html +=`<li><div class="nick">${item.lol_nick}</div></li>`;

								if(item.win_lose!==null){
									blueMatchStyle= `${item.win_lose==='WIN' ? 'blueActive' :'redActive'}`;
								}

							 }else{
								html +=`<li><div>${item.lol_nick}</div></li>`;
							 }
						}
					})


                   html +=`</ul></div>`;

	            	let blueCnt=0;
					blueTeam.forEach((item)=>{

						  if(blueCnt===0 && competitionName===item.competition_name ){

							 html +=`<div class="right_area ${blueMatchStyle}"

							 data-groupid="${item.groupId}" data-index="${index}" id="blue-${index}" data-team="BLUE"   >${item.win_lose!==null ? item.win_lose:''}</div>`;
							 ++blueCnt;
						  }
					})



                  html +=`</div> `;


					console.log("======== ");
					console.log(" ");

				});


				$("#war-record").html(html);

				$(".left_area").on("click", (e)=>{

					const winGroupId=$(e.target).attr("data-groupid");
					const team=$(e.target).attr("data-team");
					const index=$(e.target).attr("data-index");
					const loseGroupId=$(`#blue-${index}`).attr("data-groupid");
					recodeTable.updateCompetition(winGroupId, loseGroupId, team);
				})

				$(".right_area").on("click", (e)=>{

					const winGroupId=$(e.target).attr("data-groupid");
					const team=$(e.target).attr("data-team");
					const index=$(e.target).attr("data-index");
					const loseGroupId=$(`#red-${index}`).attr("data-groupid");
					recodeTable.updateCompetition(winGroupId ,loseGroupId, team);
				})
		});


	},


	updateCompetition:function(winGroupId, loseGroupId, team){
			if(confirm(`${team}팀을 WIN으로 업데이트 처리 하시겠습니까?`)){
				$.ajax({
					url:"/api/record/update",
					type:'put',
					data:{winGroupId,loseGroupId},
					success:function(res){
						if(res==="success"){
							recodeTable.searchBtn();
						}
					},
					error:function(err){
						console.error("에러 : ", err);
					}
				})
			}
	}



}

recodeTable.init();