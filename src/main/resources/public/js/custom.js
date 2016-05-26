
// preloader
$(window).load(function(){
    $('.preloader').fadeOut(1000); // set duration in brackets    
});

/* HTML document is loaded. DOM is ready. 
-------------------------------------------*/
$(function(){
	
	// ------- WOW ANIMATED ------ //
	wow = new WOW({
		mobile: false
	});
	wow.init();

	// ------- JQUERY PARALLAX ---- //
	function initParallax() {
		$('#home').parallax("100%", 0.1);
		$('#aide').parallax("100%", 0.3);
		$('#contact').parallax("100%", 0.2);
	}
	initParallax();

	// NIVO LIGHTBOX
	/*
	$('#gallery a').nivoLightbox({
			effect: 'fadeScale',
	});
	*/
});

