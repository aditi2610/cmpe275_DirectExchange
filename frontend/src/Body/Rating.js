import React from 'react';
const Rating = (props) => {

    var rating;
    if(props.rating){
        rating = props.rating;
    }
    
    var ratingsDiv;
    if(rating === 5){
       ratingsDiv = ( <span className="star-rating">
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
    5</span>);
    } else if (rating === 4){
        ratingsDiv = ( <span className="star-rating">
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star-o"></i>
    4</span>);
    } else if (rating === 3){
        ratingsDiv = ( <span className="star-rating">
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star-o"></i>
        <i className="fa fa-star-o"></i>
    3</span>);
    } else if (rating ===2){
        ratingsDiv = ( <span className="star-rating">
        <i className="fa fa-star"></i>
        <i className="fa fa-star"></i>
        <i className="fa fa-star-o"></i>
        <i className="fa fa-star-o"></i>
        <i className="fa fa-star-o"></i>
    2</span>);
    } else if (rating === 1){
        ratingsDiv = ( <span className="star-rating">
        <i className="fa fa-star"></i>
        <i className="fa fa-star-o"></i>
        <i className="fa fa-star-o"></i>
        <i className="fa fa-star-o"></i>
        <i className="fa fa-star-o"></i>
    1</span>);
    } else {
        ratingsDiv = ( <span className="star-rating">
        N/A
    </span>);
    }

    return(
    ratingsDiv
)};

export default Rating;