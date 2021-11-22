import React from 'react';
import './OHIFView.css';
//import {OHIFExtDicomMicroscopy} from '@ohif/extension-dicom-microscopy'

export default function OHIFView(){
  return(
    <div>
      <iframe src="http://localhost:3000"
        title={'OHIFView'}
        style={{width:'100%', height:900}}
      />
    </div>
  )
}