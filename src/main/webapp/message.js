/**
 * 
 */
window.onload = function () {

  getMessage();
};


setInterval(getMessage, 1000);

$(function () { 
	
	$('#send').on('click', function () {
		
	let url = new URL(window.location.href);
    let params = url.searchParams;
    var to = params.get('email');


    $.ajax({
      url: '/weChat_v1/Message',
      type: 'POST',
      data: {
		'content': $('#content').val(),
        'to': to        
      }
    })
      // Ajaxリクエストが成功した時発動
      .done((data) => {
		
		getMessage();

		$('#content').val('');
		
		
      })
      
  });
  $('#content').on('keydown', function(e) {
          if (e.key === 'Enter') {
              e.preventDefault();
              $('#send').click();
          }
     });
  });

  function getMessage() {

    let url = new URL(window.location.href);
    let params = url.searchParams;
    var to = params.get('email');

    $.ajax({
      url: '/weChat_v1/Getmessage', 
      type: 'POST',
      data: {      
        'to': to
      }
    })
      // Ajaxリクエストが成功した時発動
      .done((data) => {
        $('#history').html(data);

      })
      // Ajaxリクエストが失敗した時発動
      .fail((data) => {
        $('#history').html(data);

      })
  }