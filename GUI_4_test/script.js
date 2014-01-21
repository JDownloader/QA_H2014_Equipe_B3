$(function() {
  $( "#target" ).click(function(e) {
    var dat = $( "#messages" ).val();
    $.ajax({
      type: "POST",
      url: "127.0.0.1:8080/patient/" + $( "#noPatient" ).val() + "/prescriptions/",
      data: dat,
      success: function(msg){
        $( "#responce_text" ).css('color', 'black');
        $( "#responce_status" ).css('color', 'black');
        $( "#responce_text" ).text(XMLHttpRequest.statusText);
        $( "#responce_status" ).text(XMLHttpRequest.status);
        $( "#responce_date" ).text(getStrDate());
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        $( "#responce_text" ).css('color', 'red');
        $( "#responce_status" ).css('color', 'red');
        $( "#responce_text" ).text(XMLHttpRequest.statusText);
        $( "#responce_status" ).text(XMLHttpRequest.status);
        $( "#responce_date" ).text(getStrDate());
      }
    });
    e.preventDefault();
    return false;
  });
  function getStrDate(){
    var currentdate = new Date;
    var datetime = currentdate.getDate() +
       "/"+ (currentdate.getMonth()+1) +
       "/" + currentdate.getFullYear() +
       " @ " + currentdate.getHours() +
       ":" + currentdate.getMinutes() +
       ":" + currentdate.getSeconds();
    return datetime;
  };
});