/**
 * 
 */

$(function () {
  // Ajax button click
  $('#sh').on('click', function () {
    $.ajax({
      url: '/weChat_v1/Sh',
      type: 'POST',
      data: {
        'email': $('#email').val()
      }
    })
      // Ajaxリクエストが成功した時発動
      .done((data) => {
        $('#res').html(data);
        console.log(data);
      })
      // Ajaxリクエストが失敗した時発動
      .fail((data) => {
        $('#res').html(data);
        console.log(data);
      })

  });
});

function add_Friend() {
  var to = $('#to').text();
  //console.log(to);
  $.ajax({
    url: '/weChat_v1/Add_Frend',
    type: 'POST',
    data: {
      'to': to
    }
  })
    .done((data) => {
      $('#res').html(data);
	  if(data.includes("二人は")){
		alert('二人はすでに友達です。');
		
	  }else{
		
		alert('友達になりました！');
	  }
	 
	  
    })
    .fail((data) => {
      alert('友達になるのに失敗しました。');
    });
}