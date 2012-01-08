//
//  GCImageGrid.m
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 10/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GCImageGrid.h"

@implementation GCImageGrid
@synthesize objects, thumbSize, spacingSize, thumbCountPerRow;

-(void)setObjects:(NSArray *)_objects{
    if(objects){
        [objects release];
        objects = NULL;
    }
    if(!_objects){
        objects = NULL;
        return;
    }
    if(([_objects count] == 0)){
        objects = [[NSArray alloc] init];
        return;
    }
    NSMutableArray *array = [NSMutableArray array];
    for(id object in _objects){
        if([object isKindOfClass:[GCAsset class]])
            [array addObject:object];
    }
    objects = [[NSArray alloc] initWithArray:array];
}

-(void)closeImageView:(UITapGestureRecognizer*)sender{
    [[sender view] removeFromSuperview];
}

-(void)objectTappedAtIndex:(NSInteger)index{
    NSLog(@"object tapped at index: %i",index);
    UIImageView *image = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    [image setContentMode:UIViewContentModeScaleAspectFit];
    [image setBackgroundColor:[UIColor blackColor]];
    [image setUserInteractionEnabled:YES];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(closeImageView:)];
    [image addGestureRecognizer:tap];
    [tap release];
    [[objects objectAtIndex:index] imageForWidth:320 andHeight:480 inBackgroundWithCompletion:^(UIImage *response){
        [image setImage:response];
        [self.view addSubview:image];
        [image release];
        [self hideHUD];
    }];
}


- (id)init
{
    self = [super init];
    if (self) {
        [self setSpacingSize:2];
        [self setThumbSize:77];
        [self setThumbCountPerRow:-1];
    }
    return self;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        [self setSpacingSize:2];
        [self setThumbSize:77];
        [self setThumbCountPerRow:-1];
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}



#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    if([self thumbCountPerRow] <= 0){
        if([self spacingSize]+[self thumbSize] < self.view.frame.size.width){
            initialThumbOffset = (((int)self.view.frame.size.width+[self spacingSize])%([self thumbSize]+[self spacingSize]))/2;
            [self setThumbCountPerRow:ceil((((self.view.frame.size.width-(2*initialThumbOffset)+[self spacingSize]))/([self thumbSize]+[self spacingSize])))];
        }
        else{
            initialThumbOffset = (self.view.frame.size.width - self.thumbSize)/2;
            [self setThumbCountPerRow:1];
        }
    }
    else{
        initialThumbOffset = ((int)self.view.frame.size.width+[self spacingSize]-([self thumbCountPerRow]*([self thumbSize]+[self spacingSize])))/2;
    }
    [objectTable setAllowsSelection:NO];
    [objectTable setClipsToBounds:YES];
    [objectTable setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    [objectTable setBackgroundColor:[UIColor clearColor]];
    [objectTable setDelegate:self];
    [objectTable setDataSource:self];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

-(void)objectTappedWithGesture:(UIGestureRecognizer*)gesture{
    UIView *view = [gesture view];
    [self showHUD];
    [self objectTappedAtIndex:[view tag]];
}

-(UIView*)viewForIndexPath:(NSIndexPath*)indexPath{
    UIView *view = [[[UIView alloc] initWithFrame:CGRectMake(0, 0, objectTable.frame.size.width, [self tableView:objectTable heightForRowAtIndexPath:indexPath])] autorelease];
    int index = indexPath.row * self.thumbCountPerRow;
	int maxIndex = index + self.thumbCountPerRow-1;
    CGRect rect = CGRectMake(initialThumbOffset, self.spacingSize/2, self.thumbSize, self.thumbSize);
    int x = self.thumbCountPerRow;
    if (maxIndex >= [[self objects] count]) {
        x = x - (maxIndex - [[self objects] count]) - 1;
    }
    
    for (int i=0; i<x; i++) {
        GCAsset *asset = [[self objects] objectAtIndex:index+i];
        UIImageView *image = [[[UIImageView alloc] initWithFrame:rect] autorelease];
        [image setTag:index+i];
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(objectTappedWithGesture:)];
        [image addGestureRecognizer:tap];
        [tap release];
        [image setUserInteractionEnabled:YES];
        [image setImage:[asset thumbnail]];
        [view addSubview:image];
        rect = CGRectMake((rect.origin.x+self.thumbSize+self.spacingSize), rect.origin.y, rect.size.width, rect.size.height);
    }
    return view;
}


#pragma mark UITableViewDataSource Delegate Methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    // Return the number of sections.
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    // Return the number of rows in the section.
    if(thumbCountPerRow <= 0)
        return 0;
	if(!objects)
		return 0;
    return ceil([objects count]/((float)[self thumbCountPerRow]));
}


// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
		
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    for(UIView *v in cell.contentView.subviews){
        [v removeFromSuperview];
    }
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^(void) {
        UIView *v = [self viewForIndexPath:indexPath];
        dispatch_async(dispatch_get_main_queue(), ^(void) {
            [cell.contentView addSubview:v];
        });
    });
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
	
	return [self thumbSize]+[self spacingSize];
}

- (void) dealloc {
    [objects release];
    [super dealloc];
}
@end
