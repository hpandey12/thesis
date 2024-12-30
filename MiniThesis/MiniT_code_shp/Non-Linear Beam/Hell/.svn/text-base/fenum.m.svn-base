% Plot Node and Element Numbers 
% Roger Sauer - 14.12.10


% Fontsize
figure(fig)
fs = 10 ;


% Plot Node Numbers
for i = 1:nno
    text(PX(i,1),PX(i,2),num2str(i),'FontSize',fs,'Color',[1 0 0])
end


% Plot Element Numbers
for i = 1:nel
    
    x11 = PX(CON(i,1),1) ; x12 = PX(CON(i,2),1) ; x22 = PX(CON(i,3),1) ; x21 = PX(CON(i,4),1) ; 
    y11 = PX(CON(i,1),2) ; y12 = PX(CON(i,2),2) ; y22 = PX(CON(i,3),2) ; y21 = PX(CON(i,4),2) ;
    
    xj1 = (x21-x11)/2 + x11 ;
    xj2 = (x22-x12)/2 + x12 ;
    yj1 = (y21-y11)/2 + y11 ;
    yj2 = (y22-y12)/2 + y12 ;
 
    xji = (xj2-xj1)/2 + xj1 ;
    yji = (yj2-yj1)/2 + yj1 ;
    
    text(xji,yji,num2str(i),'FontSize',fs)
end